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
import javax.servlet.http.HttpSession;

import dao.BandInfoDao;
import dao.EachMusicDao;
import dao.LiveInfoDao;
import dao.PreparInfoDao;
import dto.BandInfo;
import dto.EachMusic;
import dto.LiveInfo;
import dto.PreparInfo;
import dto.Result;

@WebServlet("/TableShowServlet")
public class TableShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private boolean deleteFlag;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		deleteFlag = false;
		request.setCharacterEncoding("UTF-8");

		 // ログインしていなかったらログインサーブレットへ
		 HttpSession session = request.getSession();
		 if (session.getAttribute("id") == null) {
		 response.sendRedirect("/c3/LoginServlet");
		 return;
		 }

		LiveInfoDao liDao = new LiveInfoDao();
		PreparInfoDao piDao = new PreparInfoDao();
		BandInfoDao biDao = new BandInfoDao();
		EachMusicDao emDao = new EachMusicDao();
		List<PreparInfo> piList = new ArrayList<PreparInfo>();
		List<BandInfo> biList = new ArrayList<BandInfo>();
		LiveInfo li;
		int liveId = 1;
		
		// ライブを特定するような情報を得てライブ情報を取得する
		if (request.getParameter("liveid") != null) {
			liveId = Integer.parseInt(request.getParameter("liveid"));
			System.out.println("liveId:" + liveId);
		}
		
		// ライブ情報IDを参考にライブに参加する準備情報を持ってくる
		li = liDao.select(liveId);
		piList = piDao.selectByLiveInfoId(liveId);
		
		// 準備情報をもとにバンド情報を持ってくる
		for (PreparInfo pi : piList) {
			biList.add(biDao.selectById(pi.getBandInfoId()));
		}

		// それぞれをデータとして渡す
		request.setAttribute("live_info", li);
		request.setAttribute("band_infos", biList);
		request.setAttribute("prepar_infos", piList);
		
		// each_musicの情報を取得して渡す
		for (int i = 0; i < piList.size(); i++) {
			String name = "each_music[" + i + "]";
			List<EachMusic> emList = new ArrayList<EachMusic>();
			emList = emDao.select(piList.get(i));
			request.setAttribute(name, emList);
		}
			
		// タイムテーブル表示画面へフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/table_show.jsp");
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
		
		String type = request.getParameter("action_type");
		
		if (type == null || type.equals("")) {
			doGet(request, response);
		}
		
		System.out.println("フラグ:" + deleteFlag);
		
		switch(type) {
			case "delete":
				deleteFlag = true;
				deleteTimeTable(request, response);
				break;
			case "edit":
				if (!deleteFlag)
					editTimeTable(request, response);
				else
					response.sendRedirect("/c3/HomeAdminServlet");
				break;
			default:
				doGet(request, response);
		}

	}

	protected void editTimeTable(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		int liveId = 1;
		if (request.getParameter("live_info_id") != null)
			liveId = Integer.parseInt(request.getParameter("live_info_id"));

		PreparInfoDao piDao = new PreparInfoDao();
		List<PreparInfo> piList = new ArrayList<PreparInfo>();
		boolean result = false;
		
		// name属性が"prepar_id"の要素を全て順番に読み込む。
		String stringPiId[] = request.getParameterValues("prepar_info_id");
		for (int i = 0; i < stringPiId.length; i++) {
			result = piList.add(piDao.selectById(Integer.parseInt(stringPiId[i])));
			System.out.println("[" + i + "]id:" + stringPiId[i]);
			// それぞれに対して順番と転換時間を設定する。
			piList.get(i).setSetlist(i + 1);
			result = piDao.update(piList.get(i));
			System.out.println("登録：" + piList.get(i).getSetlist());
		}
		
		// ホームサーブレットで移る。
		if (result) {
			response.sendRedirect("/c3/HomeAdminServlet");
		} else {
			request.setAttribute("id", liveId);
			request.setAttribute("result", new Result("Create_failed", "保存できませんでした。", "/c3/TableShowServlet"));
			this.doGet(request, response);
		}
		
	}
	
	protected void deleteTimeTable(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		int liveId = 1;
		if (request.getParameter("live_info_id") != null)
			liveId = Integer.parseInt(request.getParameter("live_info_id"));

		PreparInfoDao piDao = new PreparInfoDao();
		List<PreparInfo> piList = new ArrayList<PreparInfo>();
		LiveInfoDao liDao = new LiveInfoDao();
		boolean result = false;
		
		// ライブ情報IDが0だったらホーム画面に戻る
		if (liveId == 0) {
			response.sendRedirect("/c3/HomeAdminServlet");
		}
		
		LiveInfo li = liDao.select(liveId);
		
		// liveIdから準備情報テーブルを持ってくる
		piList = piDao.selectByLiveInfoId(liveId);
		
		// 一件ずつ削除する
		for (PreparInfo p : piList) {
			result = piDao.delete(p.getId());
			System.out.println(p.getId() + "削除しました：" + result);
		}
		
		// ライブ情報も削除する
		result = liDao.delete(li);
		System.out.println("ライブ情報削除しました。");
		
		// ホームサーブレットで移る。
		if (result) {
			response.sendRedirect("/c3/HomeAdminServlet");
		} else {
			request.setAttribute("id", liveId);
			request.setAttribute("result", new Result("Delete_failed", "削除できませんでした。", "/c3/TableShowServlet"));
			this.doGet(request, response);
		}
		
	}
	
}
