package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.LiveInfoDao;
import dto.LiveInfo;


/**
 * Servlet implementation class HomeAdminServlet
 */
@WebServlet("/HomeAdminServlet")
public class HomeAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		HttpSession session = request.getSession();
		
	//	if (session.getAttribute("id") == null) {
	//		response.sendRedirect("/c3/LoginServlet");
	//		return;
	//	}

		//ログインユーザーIDの取得
	 // int userId = Integer.parseInt((String)session.getAttribute("id"));
		
	LiveInfoDao liveDao = new LiveInfoDao();
		
	//live_info取得
	 LiveInfo live = liveDao.selectByUserId(1);
		
		//home_admin.jsp側でのボタン表示のための処理
	request.setAttribute("live", live);
		
		// live_infoテーブルにデータがない場合
		if (live == null) {
			request.setAttribute("noLiveInfo", true);
			RequestDispatcher rd = 
					request.getRequestDispatcher("/WEB-INF/jsp/home_admin.jsp");
			rd.forward(request,response);
			return;
		}
			
		// live_infoテーブルにデータはあるが、管理者がタイムテーブルを作成していない場合
		//タイムテーブル作成画面に遷移する
		if(!live.isCreate_flag()) {
			response.sendRedirect("/c3/TableCreateServlet");
			return;
		}
		
		//live_infoテーブルにデータがあり、タイムテーブルも作成済みの場合
		//タイムテーブル表示画面へ遷移する
		response.sendRedirect("/c3/TableShowServlet");
	 
	}
}
