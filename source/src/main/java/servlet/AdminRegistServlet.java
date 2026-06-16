package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 request.setCharacterEncoding("UTF-8");

	        String user_id = request.getParameter("id");
	        String password = request.getParameter("pw");
	        int type = Integer.parseInt(request.getParameter("type"));

	        User user = new User();
	        user.setUser_id(user_id);
	        user.setPassword(password);
	        user.setType(type);

	        UserDao dao = new UserDao();
	        boolean result = dao.register(user);

	        if (result) {
	            if (type == -1) {
	                response.sendRedirect("/c3/HomeAdminServlet");
	            } else if (type >= 1) {
	                response.sendRedirect("/c3/HomeAdminServlet");
	            }
	        } else {
	        	request.setAttribute(
	        	        "result",
	        	        new Result(
	        	            "登録失敗！",
	        	            "IDが重複している可能性があります。",
	        	            "/c3/AdminRegisterServlet"
	        	        )
	        	    );

	        	    RequestDispatcher dispatcher =
	        	        request.getRequestDispatcher("/WEB-INF/jsp/AdminRegister.jsp");

	        	    dispatcher.forward(request, response);
	        	}
	    }
	}


