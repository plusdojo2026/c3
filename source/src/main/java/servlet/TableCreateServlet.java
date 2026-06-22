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
import dto.Result;

@WebServlet("/TableCreateServlet")
public class TableCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	request.setCharacterEncoding("UTF-8");
			
//			// ログインしていなかったらログインサーブレットへ
//			HttpSession session = request.getSession();
//			if (session.getAttribute("id") == null) {
//				response.sendRedirect("/c3/LoginServlet");
//				return;
//			}
	
	LiveInfoDao liDao = new LiveInfoDao();
	PreparInfoDao piDao = new PreparInfoDao();
	BandInfoDao biDao = new BandInfoDao();
	EachMusicDao emDao = new EachMusicDao();
	List<PreparInfo> piList = new ArrayList<PreparInfo>();
	List<BandInfo> biList = new ArrayList<BandInfo>();
	LiveInfo li;
	int liveId = 1;
	
	// ライブを特定するような情報を得てライブ情報を取得する
	if (request.getParameter("liveId") != null) {
		liveId = Integer.parseInt(request.getParameter("liveId"));
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
	
	// タイムテーブル作成画面へフォワードする
	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/table_create.jsp");
	dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//		// ログインしていなかったらログインサーブレットへ
		//		HttpSession session = request.getSession();
		//		if (session.getAttribute("id") == null) {
		//			response.sendRedirect("/c3/LoginServlet");
		//			return;
		//		}
		

		PreparInfoDao piDao = new PreparInfoDao();
		List<PreparInfo> piList = new ArrayList<PreparInfo>();
		boolean result = false;
		
		// 転換時間を得る
		String preparTime = "";
		if (request.getParameter("time") != null)
			preparTime = request.getParameter("time");
		
		// name属性が"prepar_id"の要素を全て順番に読み込む。
		String stringPiId[] = request.getParameterValues("prepar_id");
		for (int i = 0; i < stringPiId.length; i++) {
			result = piList.add(piDao.selectById(Integer.parseInt(stringPiId[i])));
			// それぞれに対して順番と転換時間を設定する。
			piList.get(i).setSetlist(i + 1);
			piList.get(i).setPreparTime(Integer.parseInt(preparTime));
		}
		
		// ホームサーブレットで移る。
		if (result) {
			response.sendRedirect("/c3/HomeAdminServlet");
		} else {
			int liveId = 1;
			if (request.getParameter("live_info_id") != null)
				liveId = Integer.parseInt(request.getParameter("live_info_id"));
			request.setAttribute("id", liveId);
			request.setAttribute("result", new Result("Create_failed", "作成できませんでした。", "/c3/TableCreateServlet"));
			this.doGet(request, response);
		}
	}

}
