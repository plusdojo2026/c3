package servlet;

import java.io.IOException;
import java.time.LocalDateTime;
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
import dao.LiveInfoDao;
import dao.PreparInfoDao;
import dto.LiveInfo;
import dto.LoginUser;
import dto.PreparInfo;

@WebServlet("/HomeStaffServlet")
public class HomeStaffServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
//		// ログインしていなかったらログインサーブレットへ
		HttpSession session = request.getSession();
//		if (session.getAttribute("id") == null) {
//			response.sendRedirect("/c3/LoginServlet");
//			return;
//		}
		
		// データがあるか検索する。
		// データがある場合、最もライブ開催日が近い、且つタイムテーブル作成済みのものを表示する
		
		LoginUser user = (LoginUser)session.getAttribute("id");
		
		int adminId = -1;
		
		BandInfoDao biDao = new BandInfoDao();
//		BandInfo bi = biDao.show(user.getId());	// ユーザー名検索でbiを持ってくる
		
		PreparInfoDao piDao = new PreparInfoDao();
		List<PreparInfo> adminPiList = new ArrayList<PreparInfo>();	// 自分のBandIdが登録されたPrepreデータを得る
		List<PreparInfo> tablePiList = new ArrayList<PreparInfo>();	// タイムテーブルに表示するためのPreparデータを得る
		
		LiveInfoDao liDao = new LiveInfoDao();
		List<LiveInfo> adminLiList  = new ArrayList<LiveInfo>();	// 自分が出演するライブ情報の一覧を入れる
		LiveInfo li = new LiveInfo();	// 表示するタイムテーブルのライブ情報表示
		
//		adminPiList = showAllPreparInfo(bi.getId());	// 自分のバンドIdが登録された準備情報を持ってくる。
		
		// PreparInfoに登録されたライブ情報IDからライブ情報テーブルを持ってくる。
		for (PreparInfo pi : adminPiList) {
//			myLiList.add(liDao.select(pi.getLiveId, "", "", "", ""));
		}
		
		LocalDateTime date = LocalDateTime.now();
		LiveInfo firstLi = new LiveInfo();	// 今日の日にちにを持つデータを作成する。
		firstLi.setBegin_date(date);
		
		// firstLiにデータを入れる。
		for (LiveInfo l : adminLiList ) {
			if (l.getBegin_date().isBefore(firstLi.getBegin_date())) {
				// タイムテーブルが作成済みならfirstLiに入れ替える
//				if (l.getCreate_flag())  {
//					firstLi = l;
//				}
			}
		}
		
		// 情報があればfirstLiテーブルから得られるデータを探索する
		// 情報が一つもなければデータを設定しない
		if (!firstLi.getBegin_date().equals(date)) {	// 情報がある場合
//			myPiList = piDao.showAllPreparInfo(firstLi.getId());	// ライブ情報IDからリストを持ってくる
			request.setAttribute("live_info", firstLi);
			request.setAttribute("prepar_infos", adminPiList);
		}
		
		// 出演者側ホーム画面へフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/home_band.jsp");
		dispatcher.forward(request, response);
	}

}