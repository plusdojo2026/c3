package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BandInfoDao;
import dao.EachMusicDao;
import dao.LiveInfoDao;
import dao.PreparInfoDao;
import dto.BandInfo;
import dto.EachMusic;
import dto.LiveInfo;
import dto.LoginUser;
import dto.PreparInfo;

@WebServlet("/PreparRegistServlet")
public class PreparRegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ログインしていなかったらログインサーブレットへ
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("/c3/LoginServlet");
			return;
		}

		try {
			Integer liveId = parseIntOrNull(request.getParameter("liveId"));
			if (liveId == null) {
				response.sendRedirect("LiveShowServlet");
				return;
			}

			LiveInfoDao liveDao = new LiveInfoDao();
			LiveInfo liveInfo = liveDao.select(liveId);

			LoginUser loginUser = (LoginUser) session.getAttribute("id");
			int userId = Integer.parseInt(loginUser.getId());

			BandInfoDao bandDao = new BandInfoDao();
			BandInfo bandInfo = bandDao.showBand(userId).get(0);

			request.setAttribute("live_info", liveInfo);
			request.setAttribute("band_info", bandInfo);

			if (liveInfo.getBegin_date() != null) {
				request.setAttribute("begin_date", liveInfo.getBegin_date().toString().replace("T", " "));
			}
			if (liveInfo.getEnd_date() != null) {
				request.setAttribute("end_date", liveInfo.getEnd_date().toString().replace("T", " "));
			}

			// 持ち時間（ログインバンド分）
			Integer time = null;

			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/c3?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo",
						"root", "password");

				String sql = "SELECT time FROM prepar_info WHERE live_info_id = ? AND band_info_id = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, liveId);
				ps.setInt(2, bandInfo.getId());

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					time = (Integer) rs.getObject("time");
				}

				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			request.setAttribute("time", time);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/prepar_regist.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error_message", "データ取得中にエラーが発生しました。");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	// doPost
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("/c3/LoginServlet");
			return;
		}

		request.setCharacterEncoding("UTF-8");

		try {
			Integer time = parseIntOrNull(request.getParameter("time"));
			Integer setlist = parseIntOrNull(request.getParameter("prepar_setlist"));
			String entranceMusic = request.getParameter("entrance_music");

			Integer bandId = parseIntOrNull(request.getParameter("band_info_id"));
			Integer liveId = parseIntOrNull(request.getParameter("live_info_id"));

			PreparInfo p = new PreparInfo();
			p.setTime(time);
			p.setPreparTime(null);
			p.setPreparItems("");
			p.setSetlist(setlist);
			p.setEntranceMusic(entranceMusic);
			p.setBandInfoId(bandId);
			p.setLiveInfoId(liveId);

			PreparInfoDao pDao = new PreparInfoDao();
			int preparId = pDao.insertAndReturnId(p);

			if (preparId == -1) {
				throw new Exception("準備情報の登録に失敗しました。");
			}

			String[] names = request.getParameterValues("each_name[]");
			String[] ses = request.getParameterValues("se[]");
			String[] lights = request.getParameterValues("light_req[]");
			String[] memos = request.getParameterValues("memo[]");
			String[] orders = request.getParameterValues("each_setlist[]");

			EachMusicDao mDao = new EachMusicDao();

			for (int i = 0; i < names.length; i++) {
				EachMusic m = new EachMusic();
				m.setName(names[i]);
				m.setSe(ses[i]);
				m.setLightReq(lights[i]);
				m.setMemo(memos[i]);

				Integer orderNum = parseIntOrNull(orders[i]);
				if (orderNum == null) {
					orderNum = i + 1;
				}
				m.setSetlist(orderNum);
				m.setPreparInfoId(preparId);

				if (!mDao.insert(m)) {
					throw new Exception("曲情報の登録に失敗しました（" + (i + 1) + "曲目）");
				}
			}

			response.sendRedirect("HomeBandServlet");

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error_message", "登録中にエラーが発生しました。");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	private Integer parseIntOrNull(String s) {
		try {
			if (s == null || s.isEmpty())
				return null;
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
