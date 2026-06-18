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
			
			//prepar_info取得
			PreparInfoDao preparDao = new PreparInfoDao();
			 List <PreparInfo> preparlist = preparDao.selectByLiveInfoId(1);
			 
			// prepar_infoの情報を登録
			 request.setAttribute("prepares", preparlist);
			 
			// live_show.jspにフォワードする
				RequestDispatcher rd = 
				request.getRequestDispatcher("/WEB-INF/jsp/live_show.jsp");
				rd.forward(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//①live_infoテーブルにデータがない場合
		//画面は遷移せず、アラート「まだライブの予定がありません」と画面に表示
		//live_infoのデータ取得
		LiveInfoDao liveDao = new LiveInfoDao();
		 int liveId = Integer.parseInt(request.getParameter("test"));  
		 
		//live_infoデータを格納する箱を作る
		 LiveInfo idLive = liveDao.select(liveId);
		 
		 if (idLive == null) {
				request.setAttribute("noLiveInfo", true);
				System.out.println("テスト1");
				
				 RequestDispatcher rd =
						 request.getRequestDispatcher("/WEB-INF/jsp/live_show.jsp");
				 rd.forward(request,response);
						 return;
			}
		
		//②prepar_infoテーブルにデータがない場合
		//準備情報登録画面へ遷移
				
				//prepar_infoの取得
				PreparInfoDao preparDao = new PreparInfoDao();
				int preparId = Integer.parseInt(request.getParameter("test"));
				
				//prepar_infoデータを格納する箱を作る
				PreparInfo idPrepar = preparDao.select(preparId);
				
		if (idPrepar == null) {
			request.setAttribute("noPreparInfo", true);
			System.out.println("テスト１");
			
			response.sendRedirect("/c3/PreparResistServlet");
			return;
		}
		
		//③準備情報は登録されているが、タイムテーブルが作成されていない場合
		// 画面は遷移せずアラート「まだタイムテーブルが作成されていません」の表示
		request.setAttribute("livelist", idLive);
		boolean created = false;
		
		if(!idLive.isCreate_flag()) {
			
			request.setAttribute("noTimeTable", true);
			System.out.println("テスト2");
			
			RequestDispatcher rd =
					request.getRequestDispatcher("/WEB-INF/jsp/live_show.jsp");
					rd.forward(request,response);
					return;
		}
		
		//④準備情報を登録済みでタイムテーブルが作成されている
		//タイムテーブル表示画面へ遷移
		else {
			System.out.println("テスト3");
			response.sendRedirect("/c3/TableShowServlet");
			return;
		}
	}

}
