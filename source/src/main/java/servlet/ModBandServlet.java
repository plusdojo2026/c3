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
import dto.LoginUser;
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
		partsList = ptDao.showAllParts();
		
		if (bi != null) {
			bmList = bmDao.showMember(bi);
			request.setAttribute("band_info", bi);
			request.setAttribute("band_members", bmList);
			request.setAttribute("parts", partsList);
		}
		
		// バンド情報画面へフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/mod_band.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// ログインしていなかったらログインサーブレットへ
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("/c3/LoginServlet");
			return;
		}
		
		LoginUser user = (LoginUser)session.getAttribute("id");
		BandMemberDao bmDao = new BandMemberDao();
		BandInfoDao biDao = new BandInfoDao();
		boolean result;
		
		// 既にあるバンド情報か確認し、無ければ登録を行う
		// band_idが0なら無い、1以上なら登録されている。
		int bandId = Integer.parseInt(request.getParameter("band_id"));
		// メンバーの最大値を取る
		int memberNum = Integer.parseInt(request.getParameter("member_num"));
		
		if (bandId == 0) {
			// 登録する
			// バンド情報テーブル
			String name = request.getParameter("band_name");
//			result = biDao.addBand(new BandInfo(0, name, user.getId));
					
			// メンバーの登録
			for (int i = 0; i <= memberNum; i++) {
				String memberName = request.getParameter("member_name[" + i + "]");
				int partId = Integer.parseInt(request.getParameter("parts[" + i + "]"));
				
				result = bmDao.addMember(new BandMember(0, memberName, partId, bandId));
				if (!result)
					break;
			}
			
		} else if (bandId > 0) {
			// 編集する
			// バンド情報テーブル
			String name = request.getParameter("band_name");
//			result = biDao.editBand(new BandInfo(bandId, name, user.getId));
			
			// バンドメンバーを一旦削除してから再度登録する
			// 削除する
			List<BandMember> bmList = new ArrayList<BandMember>();
			bmList = bmDao.showMember(bandId);
			for (BandMember bm : bmList) {
				result = bmDao.deleteMember(bm);
			}
			
			// 登録する
			for (int i = 0; i <= memberNum; i++) {
				String memberName = request.getParameter("member_name[" + i + "]");
				int partId = Integer.parseInt(request.getParameter("parts[" + i + "]"));
				
				result = bmDao.addMember(new BandMember(0, memberName, partId, bandId));
				if (!result)
					break;
			}
			
		}
	}
}
