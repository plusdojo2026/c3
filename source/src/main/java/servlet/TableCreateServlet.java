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

@WebServlet("/TableCreateServlet")
public class TableCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	request.setCharacterEncoding("UTF-8");
			
	//		// ログインしていなかったらログインサーブレットへ
	//		HttpSession session = request.getSession();
	//		if (session.getAttribute("id") == null) {
	//			response.sendRedirect("/c3/LoginServlet");
	//			return;
	//		}
	
	LiveInfoDao liDao = new LiveInfoDao();
	PreparInfoDao piDao = new PreparInfoDao();
	BandInfoDao biDao = new BandInfoDao();
	EachMusicDao emDao = new EachMusicDao();
	List<PreparInfo> piList = new ArrayList<PreparInfo>();
	List<BandInfo> biList = new ArrayList<BandInfo>();

	// ライブを特定するような情報を得てライブ情報を取得する
	LiveInfo li = new LiveInfo();
	
	// ライブ情報IDを参考にライブに参加する準備情報を持ってくる
	//	piList = piDao.selectByLiveInfoId(li.getId());
	li = liDao.select(1);
	piList = piDao.selectByLiveInfoId(1);
	
	// 準備情報をもとにバンド情報を持ってくる
	for (PreparInfo pi : piList) {
		System.out.println("pi:" + pi.getId() + " bi:"+ pi.getBandInfoId());
		biList.addAll(biDao.select(new BandInfo(pi.getBandInfoId(), "", 0)));
		System.out.println("biList要素数:" + biList.size());
	}

	// それぞれをデータとして渡す
	request.setAttribute("live_info", li);
	request.setAttribute("band_infos", biList);
	request.setAttribute("prepar_infos", piList);

	// 確認
	System.out.println("ライブ名：" + li.getName());
	System.out.println("バンド名表示");
	for(BandInfo b:biList) {
		System.out.println("ライブ[" + li.getId() + "]" +b.getId() + ":" + b.getName());
	}
	System.out.println("準備情報表示");
	for(PreparInfo p:piList) {
		System.out.println("[" + p.getSetlist() + "]" + p.getId() + ":" + p.getBandInfoId());
	}
	
	// each_musicの情報を取得して渡す
	for (int i = 0; i < piList.size(); i++) {
		String name = "each_music[" + i + "]";
		List<EachMusic> emList = new ArrayList<EachMusic>();
		emList = emDao.select(piList.get(i));
		request.setAttribute(name, emList);
		
		// 確認
		for (EachMusic e: emList) {
			System.out.println(piList.get(i).getId() + ":[" + e.getSetlist() + "]" + e.getName());
		}
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
		
		BandInfoDao biDao = new BandInfoDao();
		PreparInfoDao piDao = new PreparInfoDao();
		List<BandInfo> biList = new ArrayList<BandInfo>();
		List<PreparInfo> piList = new ArrayList<PreparInfo>();
		boolean result = false;
		
		// 転換時間を得る
		int preparTime = 0;
		if (request.getParameter("time") != null)
			preparTime = Integer.parseInt(request.getParameter("time"));

		// 順番を得る 
		int max = 0;
		for (int i = 0; i < max; i++) {
			piList.add(piDao.selectById(i));
		}
		
		
	}

}
