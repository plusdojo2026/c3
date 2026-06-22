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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// リクエストパラメータを取得する
		request.setCharacterEncoding("UTF-8");
		String mode = request.getParameter("mode");
		String user_id = request.getParameter("id");
		String password = request.getParameter("pw");
		//System.out.println("mode = " + mode);
		//System.out.println("user_id = " + user_id);
		
		// 登録処理を行う
		UserDao dao = new UserDao();
		if ("register".equals(mode)) {
			
			User user = new User();
			user.setUser_id(user_id);
			user.setPassword(password);
			user.setType(-2); // 
			boolean result = dao.register(user);
			
			if (result) {
				response.sendRedirect("/c3/LoginServlet");
			} else {
				request.setAttribute("result",
						new Result("登録失敗", "このIDは使用できません", "/c3/LoginServlet"));
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
				dispatcher.forward(request, response);
			}
			return;
		}
		//ログイン処理
		User user = dao.login(new User(user_id, password));
		
		if (user != null) { // ログイン成功
			// セッションスコープにIDを格納する
			
			String id = dao.select(user_id);
			LoginUser logUser = new LoginUser(id, user.getType());
			HttpSession session = request.getSession();
			session.setAttribute("id", logUser);
			
			// メニューサーブレットにリダイレクトする
			int type = user.getType();
			
			if (type == -1) {
				// 管理者
				response.sendRedirect("/c3/HomeAdminServlet");
				
			} else if (type == -2) {
				// 出演者
				response.sendRedirect("/c3/HomeBandServlet");
				
			} else if (type >= 1) {
				// 運営スタッフ
				response.sendRedirect("/c3/HomeStaffServlet");
			}
		} else { // ログイン失敗
			// リクエストスコープに、タイトル、メッセージ、戻り先を格納する
			request.setAttribute("result", new Result("ログイン失敗！", "IDまたはPWに間違いがあります。", "/c3/LoginServlet"));
			
			// 結果ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
		}
	}
}
