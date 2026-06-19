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
 * Servlet implementation class LiveShowServlet
 */
@WebServlet("/LiveShowServlet")
public class LiveShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
			HttpSession session = request.getSession();
			
			//	if (session.getAttribute("id") == null) {
			//		response.sendRedirect("/c3/LoginServlet");
			//		return;
			// }
			
			//ログインユーザーIDの取得
			//LoginUser login = (LoginUser)session.getAttribute("id"); 
			// int userId = Integer.parseInt(login.getId());
		    
			String noTimeTable = request.getParameter("noTimeTable");

			if ("true".equals(noTimeTable)) {
			    request.setAttribute("noTimeTable", true);
			}
			
		  //live_info取得
			LiveInfoDao liveDao = new LiveInfoDao();
			 List <LiveInfo> livelist = liveDao.selectByUserId(1);
		    
			
			 //live_info取得
			PreparInfoDao preparDao = new PreparInfoDao();
			List <PreparInfo> preparlist = preparDao.selectByLiveInfoId(1);
			
		 // prepar_infoの情報を登録
			
		    request.setAttribute("lives", livelist);
			 request.setAttribute("prepares", preparlist);

	

			 System.out.println("livelist size = " + livelist.size());	
				// ①live_infoテーブルにデータがない場合
		       //画面に「まだライブの予定がありません」と表示
				if (livelist == null || livelist.isEmpty()) {
					
					request.setAttribute("noLiveInfo", true);
					
					RequestDispatcher rd =
						request.getRequestDispatcher("/WEB-INF/jsp/live_show.jsp");
						rd.forward(request, response);
					System.out.println("テスト1");
					
					return;
				}
				RequestDispatcher rd =
					    request.getRequestDispatcher("/WEB-INF/jsp/live_show.jsp");
					rd.forward(request, response);
					
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// ②ライブ情報はあるが、準備情報がそろっていない場合
		//準備情報登録画面に遷移する
		int liveId = Integer.parseInt(request.getParameter("liveId"));

		PreparInfoDao preparDao = new PreparInfoDao();
		List<PreparInfo> preparlist =
		        preparDao.selectByLiveInfoId(liveId);
		
		if (preparlist == null || preparlist.isEmpty()) {
			System.out.println("テスト2");

		    request.setAttribute("noEntranceMusic", true);

		    LiveInfoDao liveDao = new LiveInfoDao();
		    List<LiveInfo> livelist =
		            liveDao.selectByUserId(1);

		    request.setAttribute("lives", livelist);

		    response.sendRedirect("/c3/PreparRegistServlet");

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
		        List<LiveInfo> livelist =
		                liveDao.selectByUserId(1);

		        request.setAttribute("lives", livelist);

		        response.sendRedirect("/c3/PreparRegistServlet");

		        return;
		    }

		//③ live_infoテーブル、prepar_infoテーブルのどちらにもデータはあるが、管理者がタイムテーブルを作成していない場合
		//画面に「まだタイムテーブルが作成されていません」と表示
		//live_infoのデータ取得
			LiveInfoDao EachLiveDao = new LiveInfoDao();
		 LiveInfo idLive = EachLiveDao.select(liveId);
		 
		 if (idLive == null) {
			    
			 response.sendRedirect("/c3/LiveShowServlet");
			    return;
			}
		 
		request.setAttribute("livelist", idLive);
		
		boolean created = false;
		if (!idLive.isCreate_flag()) {
			
			request.setAttribute("noTimeTable", true);
			System.out.println("テスト3");
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/live_show.jsp"); 
			rd.forward(request, response);
		    
			return;
			
	}
		//④live_infoテーブル、prepar_infoテーブルのどちらにもデータがあり、タイムテーブルも作成済みの場合
		//タイムテーブル表示画面に遷移する
		else {
			System.out.println("テスト4");
			response.sendRedirect("/c3/TableShowServlet");
			return;
		}
		
	}
}
