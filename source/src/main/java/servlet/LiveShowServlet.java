package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.LiveInfoDao;
import dto.LiveInfo;


/**
 * Servlet implementation class LiveShowServlet
 */
@WebServlet("/LiveShowServlet")
public class LiveShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
			HttpSession session = request.getSession();
			
			//	if (session.getAttribute("id") == null) {
			//		response.sendRedirect("/c3/LoginServlet");
			//		return;
			// }
			
			//ログインユーザーIDの取得
			//LoginUser login = (LoginUser)session.getAttribute("id"); 
			// int userId = Integer.parseInt(login.getId());
			
			//live_info取得
			LiveInfoDao liveDao = new LiveInfoDao();
			 List <LiveInfo> livelist = liveDao.selectByUserId(1);
			 
			// live_infoの情報を登録
			 request.setAttribute("lives", livelist);
			 
			
			 
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
