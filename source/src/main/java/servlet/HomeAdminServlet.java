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
import dto.LiveInfo;


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
			 List <LiveInfo> livelist = liveDao.selectByUserId(1);
			 
	// live_infoの情報を登録
			 request.setAttribute("lives", livelist);
			 
	// home_admin.jspにフォワードする
			RequestDispatcher rd = 
			request.getRequestDispatcher("/WEB-INF/jsp/home_admin.jsp");
			rd.forward(request,response);

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//live_info取得
		LiveInfoDao liveDao = new LiveInfoDao();
		 int liveId = Integer.parseInt(request.getParameter("test"));  
		 
		 //データを格納する箱を作る
		 LiveInfo idLive = liveDao.select(liveId);
			
			// live_infoテーブルにデータがない場合
	       //画面は遷移せず、アラートが表示される
			if (idLive == null) {
				request.setAttribute("noLiveInfo", true);
				System.out.println("テスト1");
				RequestDispatcher rd =
				request.getRequestDispatcher("/WEB-INF/jsp/home_admin.jsp");
				rd.forward(request,response);
				return;
			}
			
			// 準備情報がそろっていない場合、アラートが表示される
			//画面は遷移せず、アラートが表示される
			// Prepar_infoのリストをつくる →リストの中のデータは、live_infoのidをもつprepar_infoのデータ
			//　prepar_infoのデータがすべてそろっているか確認 →for文でprepar_infoのデータがあるか確かめる 
			// List<PreparInfo> preparlistVeiw = dao.selectByUserId(1);　←これはprepar.Daoに合わせて変更
			//for(PreparInfo pi:preparlistVeiw) {
				// entrance_music == null, "" →データがそろってないからアラート
			// RequestDispatcher rd =
			// request.getRequestDispatcher("/WEB-INF/jsp/home_admin.jsp");
			// rd.forward(request,response);
			// return;
			// }
			// 
			
			// live_infoテーブルにデータはあるが、管理者がタイムテーブルを作成していない場合
			//タイムテーブル作成画面に遷移する
			request.setAttribute("livelist", idLive);
			boolean created = false;
			if (!idLive.isCreate_flag()) {
				System.out.println("テスト2");
				response.sendRedirect("/c3/TableCreateServlet");
				return;
				
		}
			//live_infoテーブルにデータがあり、タイムテーブルも作成済みの場合
			//ライブ表示画面に遷移する
			else {
				System.out.println("テスト3");
				response.sendRedirect("/c3/TableShowServlet");
				return;
			}
		
	}
}
