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
import dto.PreparInfo;
import dto.Result;

@WebServlet("/LiveCreateServlet")
public class LiveCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
//		// ログインしていなかったらログインサーブレットへ
//		HttpSession session = request.getSession();
//		if (session.getAttribute("id") == null) {
//			response.sendRedirect("/c3/LoginServlet");
//			return;
//		}
		
		BandInfoDao biDao = new BandInfoDao();
		List<BandInfo> biList = new ArrayList<BandInfo>();
		
		biList = biDao.select(new BandInfo(0, "", 0));
		
		request.setAttribute("band_infos", biList);
		
		// ライブ情報作成画面へフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/live_create.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
//		// ログインしていなかったらログインサーブレットへ
		HttpSession session = request.getSession();
//		if (session.getAttribute("id") == null) {
//			response.sendRedirect("/c3/LoginServlet");
//			return;
//		}
		
		// それぞれ情報を受け取る
		String liveName = request.getParameter("live_name");
		String beginDateString = request.getParameter("begin_date");
		String endDateString = request.getParameter("end_date");
		LocalDateTime beginDate, endDate;
		beginDate = endDate = LocalDateTime.parse("1000-01-01T00:00:00");
		
		if (beginDateString != null && !beginDateString.isEmpty()) {
			beginDate = LocalDateTime.parse(beginDateString);
			System.out.println(beginDate);
		}
		
		if (endDateString != null && !endDateString.isEmpty()) {
			endDate = LocalDateTime.parse(endDateString);
			System.out.println(endDate);
		}
		System.out.println("ライブ情報を登録します");
		
		PreparInfoDao piDao = new PreparInfoDao();
		BandInfoDao biDao = new BandInfoDao();
//		LoginUser user = (LoginUser)session.getAttribute("id");
		boolean flag = false;
		boolean resultPrepar = false;
		
		// ライブ情報テーブルへ情報を登録する。
		LiveInfoDao liDao = new LiveInfoDao();
		LiveInfo li = new LiveInfo(0, liveName, beginDate, endDate, 1, flag);
//		LiveInfo li = new LiveInfo(0, liveName, beginDate, endDate, Integer.parseInt(user.getId()), flag);
		boolean resultLive = liDao.insert(li);
		
		if (!resultLive) {
			System.out.println("ライブ情報が登録できませんでした。");
			request.setAttribute("result", new Result("Create_failed", "登録できませんでした。", "/c3/LiveCreateServlet"));
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/live_create.jsp");
			dispatcher.forward(request, response);
		}
		
		List<LiveInfo> liList = liDao.select(li);
		
		for (LiveInfo l : liList) {
			li = l;
			System.out.println(l.getName());
		}
		
		System.out.println("準備情報を登録します。");
		// 準備情報テーブルにバンドID、ライブ情報ID、持ち時間のみが表示されたデータを作成する
		int performerNum = Integer.parseInt(request.getParameter("band_num"));
		for (int i = 0; i <= performerNum; i++) {
			if (request.getParameter("bandname[" + i + "]") != null && !request.getParameter("bandname[" + i + "]" ).equals("") &&
					request.getParameter("time[" + i + "]") != null) {
//				BandInfo bi = new BandInfo(0, request.getParameter("bandname[" + i + "]"), Integer.parseInt(user.getId()));
				BandInfo bi = new BandInfo(0, request.getParameter("bandname[" + i + "]"), 0);
				// バンドIDを持ってくる
				List<BandInfo> biList = biDao.select(bi);
				for (BandInfo b : biList) {
					bi = b;
					System.out.println(b.getName());
				}
				
				if (bi == null) {
					request.setAttribute("result", new Result("Create_failed", "バンドが存在しません。", "/c3/LiveCreateServlet"));
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/live_create.jsp");
					dispatcher.forward(request, response);
				}
				
				int time = Integer.parseInt(request.getParameter("time[" + i + "]"));
				System.out.println("登録します：時間[" + time + "]\tバンドID[" + bi.getId() + "]\tライブID[" + li.getId() + "]");
				resultPrepar = piDao.insert(new PreparInfo(0, time, 0, "", 0, "", bi.getId(), li.getId()));
				
				if (!resultPrepar) {
					break;
				}
			}
		}	
		
//		// ホームサーブレットへ戻る。
		if (resultPrepar && resultLive) {
			response.sendRedirect("/c3/HomeAdminServlet");
		} else {
			request.setAttribute("result", new Result("Create_failed", "登録できませんでした。", "/c3/LiveCreateServlet"));
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/live_create.jsp");
			dispatcher.forward(request, response);
		}
		
	}

}
