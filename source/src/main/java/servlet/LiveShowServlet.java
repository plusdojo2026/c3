package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BandInfoDao;
import dao.LiveInfoDao;
import dao.PreparInfoDao;
import dto.BandInfo;
import dto.LiveInfo;
import dto.LoginUser;
import dto.PreparInfo;


/**
 * Servlet implementation class LiveShowServlet
 */
@WebServlet("/LiveShowServlet")
public class LiveShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//ボタンの状態判定
		 private Map<Integer, String> createStatusMap(List<LiveInfo> livelist) {
		 Map<Integer, String> statusMap = new HashMap<>();
		 
		 PreparInfoDao preparDao = new PreparInfoDao();
		 
		 if (livelist != null) {
			 for (LiveInfo live : livelist) {
				 List<PreparInfo> preparlist = preparDao.selectByLiveInfoId(live.getId());
				 
				 //テスト2:準備情報未登録
				 if (preparlist == null || preparlist.isEmpty()) {
					 statusMap.put(live.getId(), "TEST2");
					 continue;
				 }
				 
				 //テスト2:入場曲未入力
				 boolean hasError = preparlist.stream().anyMatch(pi ->
				 pi.getEntranceMusic() == null || pi.getEntranceMusic().trim().isEmpty());
				 
				 if(hasError) {
					 statusMap.put(live.getId(), "TEST2");
					 continue;
				 }
				 
				 //テスト3:タイムテーブル未作成
				 if (!live.isCreate_flag()) {
					 statusMap.put(live.getId(), "TEST3");
				 }
				 
				 //テスト4:タイムテーブル作成済み
				 else {
					 statusMap.put(live.getId(), "TEST4");
				 }
			 }
		 }
		 return statusMap;
		 }
		 
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//もしもログインしていなかったらログインサーブレットにリダイレクトする
				HttpSession session = request.getSession();
				
			if (session.getAttribute("id") == null) {
				response.sendRedirect("/c3/LoginServlet");
					return;
			}


			//ログインユーザーIDの取得
				LoginUser login = (LoginUser)session.getAttribute("id"); 
				int userId = Integer.parseInt(login.getId());
				
				//band_infoデータの取得
				BandInfoDao bandDao = new BandInfoDao();
				 List<BandInfo>bandlist = bandDao.showBand(userId);
				 
				 //prepar_infoデータの取得
				 PreparInfoDao preparDao = new PreparInfoDao();
				 List<PreparInfo>preparlist = preparDao.selectByBandId(bandlist.get(0).getId());
				 
			String noTimeTable = request.getParameter("noTimeTable");

			if ("true".equals(noTimeTable)) {
			    request.setAttribute("noTimeTable", true);
			}
			
		  //live_info取得
			LiveInfoDao liveDao = new LiveInfoDao();
			
	        
	        List<LiveInfo>livelist = new ArrayList<LiveInfo>();
	        for (PreparInfo pi: preparlist) {
	        	LiveInfo liveTemp = liveDao.select(pi.getLiveInfoId());
	        	livelist.add(liveTemp);
	        }
	        
	        Map<Integer, String> statusMap= createStatusMap(livelist);
	        request.setAttribute("statusMap", statusMap);
			
		 // prepar_infoの情報を登録
			
		    request.setAttribute("lives", livelist);
		

	

			 System.out.println("livelist size = " + livelist.size());	
				// ①live_infoテーブルにデータがない場合
		       //画面に「まだライブの予定がありません」と表示
				if (livelist == null || livelist.isEmpty()) {
					
					request.setAttribute("noLiveInfo", true);
					
					RequestDispatcher rd =
						request.getRequestDispatcher("/WEB-INF/jsp/live_show.jsp");
						rd.forward(request, response);
					System.out.println("テスト1");
					
					return;
				}
				RequestDispatcher rd =
					    request.getRequestDispatcher("/WEB-INF/jsp/live_show.jsp");
					rd.forward(request, response);
					
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		LoginUser login = (LoginUser)session.getAttribute("id");
	    int userId = Integer.parseInt(login.getId());

	    int liveId = Integer.parseInt(request.getParameter("liveId"));
	    
		// ②ライブ情報はあるが、準備情報がそろっていない場合
		//準備情報登録画面に遷移する

		PreparInfoDao preparDao = new PreparInfoDao();
		List<PreparInfo> preparlist =
		        preparDao.selectByLiveInfoId(liveId);
		
		if (preparlist == null || preparlist.isEmpty()) {
			System.out.println("テスト2");

		    request.setAttribute("noEntranceMusic", true);

		    LiveInfoDao liveDao = new LiveInfoDao();
		   
	        List<LiveInfo>livelist = liveDao.selectByUserId(userId);

		    request.setAttribute("lives", livelist);

		    response.sendRedirect(
		    		request.getContextPath()
		    		+ "/PreparRegistServlet?liveId="
		    		+ liveId);

		    return;
		}
		
		boolean hasError = preparlist.stream().anyMatch(pi ->
		pi.getEntranceMusic() == null ||
		pi.getEntranceMusic().isEmpty()
				);
				
		
			if(hasError) {

		        request.setAttribute("noEntranceMusic", true);
		        System.out.println("テスト2");
		        LiveInfoDao liveDao = new LiveInfoDao();
		        
		        List<LiveInfo>livelist = liveDao.selectByUserId(userId);

		        request.setAttribute("lives", livelist);

		        response.sendRedirect(
		        		request.getContextPath()
		        		+ "/PreparRegistServlet?liveId="
		        		+ liveId);

		        return;
		    }

		//③ live_infoテーブル、prepar_infoテーブルのどちらにもデータはあるが、管理者がタイムテーブルを作成していない場合
		//画面にアラートで「まだタイムテーブルが作成されていません」と表示
		//live_infoのデータ取得
			LiveInfoDao eachLiveDao = new LiveInfoDao();
			LiveInfo idLive = eachLiveDao.select(liveId);

			if (idLive == null) {

			    response.sendRedirect(
			            request.getContextPath()
			            + "/LiveShowServlet");
			    return;
			}

			request.setAttribute("livelist", idLive);

			if (!idLive.isCreate_flag()) {

			    BandInfoDao bandDao = new BandInfoDao();
			    List<BandInfo> bandlist = bandDao.showBand(userId);

			    PreparInfoDao pDao = new PreparInfoDao();
			    List<PreparInfo> bandPreparList =
			            pDao.selectByBandId(bandlist.get(0).getId());

			    LiveInfoDao liveDao = new LiveInfoDao();
			    List<LiveInfo> livelist = new ArrayList<>();

			    for (PreparInfo pi : bandPreparList) {
			        LiveInfo live = liveDao.select(pi.getLiveInfoId());

			        if (live != null) {
			            livelist.add(live);
			        }
			    }

			    request.setAttribute("lives", livelist);

			    Map<Integer, String> statusMap = createStatusMap(livelist);
			    request.setAttribute("statusMap", statusMap);

			    request.setAttribute("noTimeTable", true);

			    System.out.println("テスト3");

			    RequestDispatcher rd =
			            request.getRequestDispatcher("/WEB-INF/jsp/live_show.jsp");
			    rd.forward(request, response);

			    return;
			}
		//④live_infoテーブル、prepar_infoテーブルのどちらにもデータがあり、タイムテーブルも作成済みの場合
		//タイムテーブル表示画面に遷移する
		else {
			System.out.println("テスト4");
			response.sendRedirect(
					request.getContextPath()
					+ "/TableShowBandServlet?liveId="
					+ liveId);
			return;
		}
		
	}
}
