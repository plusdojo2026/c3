package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BandInfoDao;
import dao.LiveInfoDao;
import dao.PreparInfoDao;
import dto.LoginUser;

@WebServlet("/LiveCreateServlet")
public class LiveCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// ログインしていなかったらログインサーブレットへ
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("/c3/LoginServlet");
			return;
		}
		
		// ライブ情報作成画面へフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/live_create.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// ログインしていなかったらログインサーブレットへ
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("/c3/LoginServlet");
			return;
		}
		
		// それぞれ情報を受け取る
		String liveName = request.getParameter("live_name");
		String beginDate = request.getParameter("begin_date");
		String endDate = request.getParameter("end_date");
		
		// ライブ情報テーブルへ情報を登録する。
		LiveInfoDao liDao = new LiveInfoDao();
//		LiveInfo li = new LiveInfo(0, liveName, beginDate, endDate, (LoginUser)session.getAttribute("id"));
//		boolean resultLive = liDao.addLibeInfo(li);
		
		// 準備情報テーブルにバンドID、ライブ情報ID、持ち時間のみが表示されたデータを作成する
		PreparInfoDao piDao = new PreparInfoDao();
		BandInfoDao biDao = new BandInfoDao();
		LoginUser user = (LoginUser)session.getAttribute("id");
		boolean resultPrepar;
		
		int performerNum = Integer.parseInt(request.getParameter("band_num"));
		for (int i = 0; i <= performerNum; i++) {
			if (request.getParameter("bandname[" + i + "]") != null && !request.getParameter("bandname[" + i + "]" ).equals("") &&
					request.getParameter("time[" + i + "]") != null) {
//				BandInfo bi = new BandInfo(0, request.getParameter("bandname[" + i + "]"), user.getId());
//				int bandId = biDao.showId(bi);
				int time = Integer.parseInt(request.getParameter("time[" + i + "]"));
//				int liveId = liDao.showId(li);
				
//				resultPrepar = piDao.addPreparInfo(new PreparInfo(0, time, 0, "", 0, "", bandId, liveId));
				
//				if (!resultPrepar) {
//					break;
//				}
			}
		}
		
		/*
		 * if (resultLive && resultPrepar) {
		 * response.sendRedirect("/c3/HomeAdminServlet"); } else {
		 * request.setAttribute("result", "登録できませんでした。"); RequestDispatcher dispatcer =
		 * request.getRequestDispatcher("/WEB-INF/jsp/live_create.jsp");
		 * dispatcer.forward(request, response); }
		 */

		
	}

}
