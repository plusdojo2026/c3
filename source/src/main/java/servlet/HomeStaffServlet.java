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
import dto.BandInfo;
import dto.LiveInfo;
import dto.LoginUser;
import dto.PreparInfo;

@WebServlet("/HomeStaffServlet")
public class HomeStaffServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// ログインしていなかったらログインサーブレットへ
				HttpSession session = request.getSession();
				if (session.getAttribute("user") == null) {
				    response.sendRedirect("/c3/LoginServlet");
				    return;
				}

				// ★ LoginUser を取得（User ではない）
				LoginUser login = (LoginUser)session.getAttribute("user");

				// ★ LoginUser.id は users.id（数値）を文字列で保持している
				int userId = Integer.parseInt(login.getId());

				// BandInfo を取得
				BandInfoDao biDao = new BandInfoDao();
				List<BandInfo> biList = biDao.showBand(userId);
		
		PreparInfoDao piDao = new PreparInfoDao();
		List<PreparInfo> myPiList = new ArrayList<PreparInfo>();	// 自分のBandIdが登録されたPreparデータを得る
		List<PreparInfo> tablePiList = new ArrayList<PreparInfo>();	// タイムテーブルに表示するためのPreparデータを得る
		
		LiveInfoDao liDao = new LiveInfoDao();
		List<LiveInfo> myLiList  = new ArrayList<LiveInfo>();	// 自分が出演するライブ情報の一覧を入れる
		LiveInfo li = new LiveInfo();	// 表示するタイムテーブルのライブ情報表示
		
		for (BandInfo band : biList) {
		   myPiList.addAll(piDao.selectByBandId(band.getId()));
		}
		
		
		// PreparInfoに登録されたライブ情報IDからライブ情報テーブルを持ってくる。
		for (PreparInfo pi : myPiList) {
			myLiList.add(liDao.select(pi.getLiveInfoId()));
		}
		
		
		LocalDateTime date = LocalDateTime.now();
		LiveInfo firstLi = new LiveInfo();	// 今日の日にちにを持つデータを作成する。
		firstLi.setBegin_date(date);
		
		// firstLiにデータを入れる。
		for (LiveInfo l : myLiList ) {
			if (l.getBegin_date().isBefore(firstLi.getBegin_date())) {
				// タイムテーブルが作成済みならfirstLiに入れ替える
				if (l.isCreate_flag())  {
					firstLi = l;
				}
			}
		}
		
		// 情報があればfirstLiテーブルから得られるデータを探索する
		// 情報が一つもなければデータを設定しない
		if (!firstLi.getBegin_date().equals(date)) {	// 情報がある場合
			LiveInfo LiList = liDao.select(firstLi.getId());	// ライブ情報IDからリストを持ってくる
			request.setAttribute("band_infos", biList);
			request.setAttribute("live_info", firstLi);
			request.setAttribute("prepar_infos", myPiList);
		}
		
		// 出演者側ホーム画面へフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/home_staff.jsp");
		dispatcher.forward(request, response);
	}

}