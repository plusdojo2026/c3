package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BandInfoDao;
import dao.EachMusicDao;
import dao.LiveInfoDao;
import dao.PreparInfoDao;
import dto.BandInfo;
import dto.EachMusic;
import dto.LiveInfo;
import dto.PreparInfo;

@WebServlet("/TableShowBandServlet")
public class TableShowBandServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// // ログインしていなかったらログインサーブレットへ
		// HttpSession session = request.getSession();
		// if (session.getAttribute("id") == null) {
		// response.sendRedirect("/c3/LoginServlet");
		// return;
		// }

		LiveInfoDao liDao = new LiveInfoDao();
		PreparInfoDao piDao = new PreparInfoDao();
		BandInfoDao biDao = new BandInfoDao();
		List<PreparInfo> piList = new ArrayList<PreparInfo>();
		List<BandInfo> biList = new ArrayList<BandInfo>();

		// ライブを特定するような情報を得てライブ情報を取得する
		LiveInfo li = new LiveInfo();

		// ライブ情報IDを参考にライブに参加する準備情報を持ってくる
//		piList = piDao.select(li);

		// 準備情報をもとにバンド情報を持ってくる
		for (PreparInfo pi : piList) {
//			biList.add(biDao.select(new BandInfo(pi.getBandInfoId(), "", 0)));
		}

		// それぞれをデータとして渡す
		request.setAttribute("live_info", li);
		request.setAttribute("band_infos", biList);
		request.setAttribute("prepar_infos", piList);

		// タイムテーブル作成画面へフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/table_show_band.jsp");
		dispatcher.forward(request, response);
	}

}
