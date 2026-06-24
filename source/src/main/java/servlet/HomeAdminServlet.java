package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.LiveInfoDao;
import dao.PreparInfoDao;
import dto.LiveInfo;
import dto.PreparInfo;

/**
 * Servlet implementation class HomeAdminServlet
 */
@WebServlet("/HomeAdminServlet")
public class HomeAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		HttpSession session = request.getSession();
		
	//	if (session.getAttribute("id") == null) {
	//		response.sendRedirect("/c3/LoginServlet");
	//		return;
	//	}


	//ログインユーザーIDの取得
		//LoginUser login = (LoginUser)session.getAttribute("id"); 
		// int userId = Integer.parseInt(login.getId());
		
	//live_info取得
			LiveInfoDao liveDao = new LiveInfoDao();
			 LiveInfo condition = new LiveInfo();
			 condition.setUser_id(0);
			
			 List<LiveInfo> livelist = liveDao.select(condition);
			 //各ライブの状態判定
			 PreparInfoDao preparDao = new PreparInfoDao();
			 
			 for (LiveInfo live : livelist) {
				 
				 List<PreparInfo> preparlist =preparDao.selectByLiveInfoId(live.getId());
				 
				 //テスト2:準備情報なし
				if (preparlist == null || preparlist.isEmpty()) {
					live.setStatus("TEST2");
					continue;
				}
				 //テスト2:入場曲未入力
				 boolean hasError = preparlist.stream().anyMatch(pi ->
				 pi.getEntranceMusic() == null ||
				 pi.getEntranceMusic().trim().isEmpty());
				 
				 if (hasError) {
					 live.setStatus("TEST2");
					 continue;
				 }
				 
				 //テスト3:タイムテーブル未作成
				 if (!live.isCreate_flag()) {
					 live.setStatus("TEST3");
				 }
				 
				 //テスト4:タイムテーブル作成済み
				 else {
					 live.setStatus("TEST4");
				 }
			 }	 
			 // live_infoの情報を登録
			 request.setAttribute("lives", livelist);
			
			
			// ①live_infoテーブルにデータがない場合
	       //画面に「ライブ情報作成画面からライブ情報を登録してください」と表示
			
			if (livelist == null || livelist.isEmpty()) {
				
				request.setAttribute("noLiveInfo", true);
				
				RequestDispatcher rd =
					request.getRequestDispatcher("/WEB-INF/jsp/home_admin.jsp");
					rd.forward(request, response);
				System.out.println("テスト1");
				
				return;
			}
			RequestDispatcher rd =
				    request.getRequestDispatcher("/WEB-INF/jsp/home_admin.jsp");
				rd.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		
		// ②ライブ情報はあるが、準備情報がそろっていない場合、アラートが表示される
		//画面は遷移せず、アラート「出演者からの準備情報が不足しています」が表示される
		int liveId = Integer.parseInt(request.getParameter("liveId"));

		PreparInfoDao preparDao = new PreparInfoDao();
		List<PreparInfo> preparlist =
		        preparDao.selectByLiveInfoId(liveId);
		
		if (preparlist == null || preparlist.isEmpty()) {
			System.out.println("テスト2");

		    request.setAttribute("noEntranceMusic", true);

		    LiveInfoDao liveDao = new LiveInfoDao();
		    LiveInfo condition = new LiveInfo();
		    condition.setUser_id(0);
	        
	        List<LiveInfo>livelist = liveDao.select(condition);
		   request.setAttribute("lives", livelist);

		    RequestDispatcher rd =
		            request.getRequestDispatcher("/WEB-INF/jsp/home_admin.jsp");
		    rd.forward(request, response);

		    return;
		}
		
		boolean hasError = preparlist.stream().anyMatch(pi ->
		pi.getEntranceMusic() == null ||
		pi.getEntranceMusic().isEmpty()
				);
				
		
			if(hasError) {

		        request.setAttribute("noEntranceMusic", true);
		        System.out.println("テスト2");
		        LiveInfoDao liveDao = new LiveInfoDao();
		        LiveInfo condition = new LiveInfo();
		        condition.setUser_id(0);
		        
		        List<LiveInfo>livelist = liveDao.select(condition);

		        request.setAttribute("lives", livelist);

		        RequestDispatcher rd =
		                request.getRequestDispatcher("/WEB-INF/jsp/home_admin.jsp");
		        rd.forward(request, response);

		        return;
		    }

		//③ live_infoテーブル、prepar_infoテーブルのどちらにもデータはあるが、管理者がタイムテーブルを作成していない場合
		//タイムテーブル作成画面に遷移する
		//live_infoのデータ取得
			LiveInfoDao EachLiveDao = new LiveInfoDao();
		 LiveInfo idLive = EachLiveDao.select(liveId);
		 
		 if (idLive == null) {
			    
			    response.sendRedirect("/c3/HomeAdminServlet");
			    return;
			}
		 
		request.setAttribute("livelist", idLive);
		
		boolean created = false;
		
		System.out.println(idLive.isCreate_flag());
		System.out.println(idLive.getName());
		if (!idLive.isCreate_flag()) {
			System.out.println("テスト3");
			response.sendRedirect(
					request.getContextPath()
				+	"/TableCreateServlet?liveId="
				+ liveId);
			return;
			
	}
		//④live_infoテーブル、prepar_infoテーブルのどちらにもデータがあり、タイムテーブルも作成済みの場合
		//タイムテーブル表示画面に遷移する
		else {
			System.out.println("テスト4");
			response.sendRedirect(
					request.getContextPath()
					+ "/TableShowServlet?liveid="
					+ liveId);
			return;
		}
		
	}
}
