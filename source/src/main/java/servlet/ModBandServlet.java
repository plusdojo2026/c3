package servlet;

import java.io.IOException;
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
import dao.BandMemberDao;
import dao.PartsDao;
import dto.BandInfo;
import dto.BandMember;
import dto.Parts;

@WebServlet("/ModBandServlet")
public class ModBandServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// ログインしていなかったらログインサーブレットへ
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("/c3/LoginServlet");
			return;
		}
		
		// 登録されているデータがあるか検索し、登録されたデータがあった場合その内容をリクエストスコープに登録する
		BandInfoDao biDao = new BandInfoDao();
		BandMemberDao bmDao = new BandMemberDao();
		PartsDao ptDao = new PartsDao();
		BandInfo bi = new BandInfo();
		List<BandMember> bmList = new ArrayList<BandMember>();
		List<Parts> partsList = new ArrayList<Parts>();
		
//		bi = biDao.showBandInfo((LoginUser)session.getAttribute("id"));
//		partsList = ptDao.showAllParts();
		
		if (bi != null) {
			bmList = bmDao.showMember(bi);
			request.setAttribute("band_info", bi);
			request.setAttribute("band_members", bmList);
			request.setAttribute("parts", ptDao);
		}
		
		// バンド情報画面へフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/mod_band.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
