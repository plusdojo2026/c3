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
import dto.Result;

@WebServlet("/ModBandServlet")
public class ModBandServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
//		// ログインしていなかったらログインサーブレットへ
		HttpSession session = request.getSession();
//		if (session.getAttribute("id") == null) {
//			response.sendRedirect("/c3/LoginServlet");
//			return;
//		}
		
		// 登録されているデータがあるか検索し、登録されたデータがあった場合その内容をリクエストスコープに登録する
		BandInfoDao biDao = new BandInfoDao();
		BandMemberDao bmDao = new BandMemberDao();
		PartsDao ptDao = new PartsDao();
		BandInfo bi = null;
		List<BandMember> bmList = new ArrayList<BandMember>();
		List<Parts> partsList = new ArrayList<Parts>();
		LoginUser user = (LoginUser)session.getAttribute("id");
		List<BandInfo> biList= new ArrayList<BandInfo>();
		int biNum = 4;
		
		if (user != null && user.getId() != "") {
			biList = biDao.showBand(Integer.parseInt(user.getId()));
			for (BandInfo b : biList) {
				bi = b;
			}
		}
		partsList = ptDao.showAllParts();
		
		if (bi != null) {
			bmList = bmDao.showMember(bi);
			request.setAttribute("band_info_id", bi.getId());
			request.setAttribute("band_info_name", bi.getName());
			request.setAttribute("band_members", bmList);
			request.setAttribute("parts", partsList);
		} else {
			bmList = bmDao.showMember(biNum);
			bi = biDao.selectById(biNum);
			request.setAttribute("band_info_id", biNum);
			request.setAttribute("band_info_name", bi.getName());
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
//		if (session.getAttribute("id") == null) {
//			response.sendRedirect("/c3/LoginServlet");
//			return;
//		}
		
		LoginUser user = null;
		if (session.getAttribute("id") != null) {
			user = (LoginUser)session.getAttribute("id");
		}
		BandMemberDao bmDao = new BandMemberDao();
		BandInfoDao biDao = new BandInfoDao();
		boolean result = false;
		
		// 既にあるバンド情報か確認し、無ければ登録を行う
		// band_idが0なら無い、1以上なら登録されている。
		int bandId = Integer.parseInt(request.getParameter("band_id"));
		// メンバーの最大値を取る
		int memberNum = Integer.parseInt(request.getParameter("member_num"));
		
		if (bandId == 0) {	// 未登録のバンドである場合
			// 登録する
			// バンド情報テーブル
			String name = request.getParameter("band_name");
			if (user != null) {
				result = biDao.insert(new BandInfo(0, name, Integer.parseInt(user.getId())));
			}
			
			// バンドIDを取得する
			BandInfo bi = null;
			if (user != null) {
				bi = (BandInfo)biDao.select(new BandInfo(0, name, Integer.parseInt(user.getId())));
			}
			
			// メンバーの登録
			for (int i = 0; i <= memberNum; i++) {
				String memberName = "";
				int partId = 0;
				if (request.getParameter("member_name[" + i + "]") != null)
					memberName = request.getParameter("member_name[" + i + "]");

				if (request.getParameter("parts[" + i + "]") != null)
					partId = Integer.parseInt(request.getParameter("parts[" + i + "]"));

				if (!memberName.equals("") && partId != 0) {
					System.out.println(i + "登録します");
					if (user != null) {
						result = bmDao.addMember(new BandMember(0, memberName, partId, bi.getId()));
					}
					result = bmDao.addMember(new BandMember(0, memberName, partId, 5));
				} else {
					System.out.println(i + "：データがありません");
				}
				
				if (!result)
					break;
			}
			
		} else if (bandId > 0) {	// 既に登録されているバンドである場合
			// 編集する
			// バンド情報テーブル
			String name = request.getParameter("band_name");
			if (user != null) {
				result = biDao.update(new BandInfo(bandId, name, Integer.parseInt(user.getId())));
			}
			// バンドメンバーを一旦削除してから再度登録する
			// 削除する
			List<BandMember> bmList = new ArrayList<BandMember>();
			bmList = bmDao.showMember(bandId);
			for (BandMember bm : bmList) {
				result = bmDao.deleteMember(bm);
			}
			
			// 登録する
			for (int i = 0; i <= memberNum; i++) {
				String memberName = "";
				int partId = 0;
				if (request.getParameter("member_name[" + i + "]") != null)
					memberName = request.getParameter("member_name[" + i + "]");

				if (request.getParameter("parts[" + i + "]") != null)
					partId = Integer.parseInt(request.getParameter("parts[" + i + "]"));
				
				if (!memberName.equals("") && partId != 0) {
					System.out.println(i + "登録します");
					result = bmDao.addMember(new BandMember(0, memberName, partId, bandId));
				} else {
					System.out.println(i + "：データがありません");
				}
				if (!result)
					break;
			}
			
		}
		
		// ホームサーブレットへ戻る。
		if (result) {
			response.sendRedirect("/c3/HomeBandServlet");
		} else {
			PartsDao ptDao = new PartsDao();
			List<Parts> partsList = new ArrayList<Parts>();
			partsList = ptDao.showAllParts();
			request.setAttribute("parts", partsList);
			request.setAttribute("result", new Result("Mod_failed", "登録できませんでした。", "/c3/ModBandServlet"));
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/mod_band.jsp");
			dispatcher.forward(request, response);
		}
	}
}
