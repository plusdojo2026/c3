package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import dto.LoginUser;
import dto.Result;
import dto.User;

/**
 * Servlet implementation class AdminRegistServlet
 */
@WebServlet("/AdminRegistServlet")
public class AdminRegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminRegistServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin_regist.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		// ログインしていなかったらログインサーブレットへ
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("/c3/LoginServlet");
			return;
		}
		
		String mode = request.getParameter("mode");
		String user_id = request.getParameter("id");
		String password = request.getParameter("pw");
		//int type = Integer.parseInt(request.getParameter("type"));
		if (user_id == null || user_id.trim().isEmpty()
		        || password == null || password.trim().isEmpty()) {

		    request.setAttribute("result",
		            new Result("入力エラー", "IDとパスワードは必須です", "/c3/AdminRegistServlet"));

		    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin_regist.jsp");
		    dispatcher.forward(request, response);
		    return;
		}
		UserDao dao = new UserDao();
		if ("login".equals(mode)) {
			
			User user = new User();
			user.setUser_id(user_id);
			user.setPassword(password);
			user.setType(-1); // 
			boolean result = dao.register(user);
			
			if (result) {
				response.sendRedirect("/c3/HomeAdminServlet");
			} else {
				request.setAttribute("result",
						new Result("登録失敗", "このIDは使用できません", "/c3/HomeAdminServlet"));
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin_regist.jsp");
				dispatcher.forward(request, response);
			}
			return;
		}
		
		if ("register".equals(mode)) {
			
			User user = new User();
			user.setUser_id(user_id);
			user.setPassword(password);
			
			LoginUser logUser = (LoginUser)session.getAttribute("id");
			System.out.println(logUser.getId());
			user.setType(Integer.parseInt(logUser.getId()));
			
			boolean result = dao.register(user);
			
			if (result) {
				response.sendRedirect("/c3/HomeAdminServlet");
			} else {
				request.setAttribute("result",
						new Result("登録失敗", "このIDは使用できません", "/c3/HomeAdminServlet"));
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin_regist.jsp");
				dispatcher.forward(request, response);
			}
			return;
		}
		
	}
}
