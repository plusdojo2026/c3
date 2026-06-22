package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EachMusicDao;
import dao.PreparInfoDao;
import dto.EachMusic;
import dto.PreparInfo;

@WebServlet("/PreparRegistServlet")
public class PreparRegistServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/prepar_regist.jsp");
		dispatcher.forward(request, response);
		
		
	}   
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            // 基本情報の取得（フォームから飛んでくるやつ）
            Integer time = parseIntOrNull(request.getParameter("time"));
            Integer setlist = parseIntOrNull(request.getParameter("prepar_setlist"));
            String entranceMusic = request.getParameter("entrance_music");

            // JSP に hidden で埋め込む前提
            Integer bandId = parseIntOrNull(request.getParameter("band_info_id"));
            Integer liveId = parseIntOrNull(request.getParameter("live_info_id"));

            // PreparInfo を DTO に詰める
            PreparInfo p = new PreparInfo();
            p.setTime(time);
            p.setPreparTime(null);      // 今は使わないので null
            p.setPreparItems("");       // 同上
            p.setSetlist(setlist);
            p.setEntranceMusic(entranceMusic);
            p.setBandInfoId(bandId);
            p.setLiveInfoId(liveId);
            		
            // 準備情報を DB に登録 → 自動採番IDを取得
            PreparInfoDao pDao = new PreparInfoDao();
            int preparId = pDao.insertAndReturnId(p);

            if (preparId == -1) {
                throw new Exception("準備情報の登録に失敗しました。");
            }

            // 曲情報の配列を受け取る
            String[] names = request.getParameterValues("each_name[]");
            String[] ses = request.getParameterValues("se[]");
            String[] lights = request.getParameterValues("light_req[]");
            String[] memos = request.getParameterValues("memo[]");
            String[] orders = request.getParameterValues("each_setlist[]"); // 数字だけ送る前提

            EachMusicDao mDao = new EachMusicDao();

            // ⑤ 曲情報を1曲ずつ登録
            for (int i = 0; i < names.length; i++) {

                EachMusic m = new EachMusic();
                m.setName(names[i]);
                m.setSe(ses[i]);
                m.setLightReq(lights[i]);
                m.setMemo(memos[i]);

                // 曲順は数字だけ送ってくる前提
                Integer orderNum = parseIntOrNull(orders[i]);
                if (orderNum == null) {
                    orderNum = i + 1; // 念のための保険
                }
                m.setSetlist(orderNum);

                m.setPreparInfoId(preparId);

                if (!mDao.insert(m)) {
                    throw new Exception("曲情報の登録に失敗しました（" + (i + 1) + "曲目）");
                }
            }
     
            // 出演者ホーム画面へ戻す
            response.sendRedirect("home_band.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error_message", "登録中にエラーが発生しました。");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    // 数字変換の補助メソッド（空文字や null に強い）
    private Integer parseIntOrNull(String s) {
        try {
            if (s == null || s.isEmpty()) return null;
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
