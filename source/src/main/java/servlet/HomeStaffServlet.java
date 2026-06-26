package servlet;

import java.io.IOException;
import java.time.LocalDateTime;
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
import dao.EachMusicDao;
import dao.LiveInfoDao;
import dao.PreparInfoDao;
import dto.BandInfo;
import dto.EachMusic;
import dto.LiveInfo;
import dto.LoginUser;
import dto.PreparInfo;

@WebServlet("/HomeStaffServlet")
public class HomeStaffServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // ログインチェック
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect("/c3/LoginServlet");
            return;
        }

        LoginUser login = (LoginUser) session.getAttribute("user");
        int userId = Integer.parseInt(login.getId());
        int type = login.getType();

        // DAO
        BandInfoDao biDao = new BandInfoDao();
        PreparInfoDao piDao = new PreparInfoDao();
        LiveInfoDao liDao = new LiveInfoDao();
        EachMusicDao emDao = new EachMusicDao();

        List<BandInfo> biList;
        
     // 全ライブ取得
        List<LiveInfo> liveList = liDao.select(new LiveInfo());

        // 現在日時
        LocalDateTime now = LocalDateTime.now();

        // 直近ライブ
        LiveInfo nearestLive = null;

        for (LiveInfo live : liveList) {

            // 終了済みは除外
            if (live.getEnd_date().isBefore(now)) {
                continue;
            }

            // 作成済みだけ対象
            if (!live.isCreate_flag()) {
                continue;
            }

            if (nearestLive == null ||
                live.getBegin_date().isBefore(nearestLive.getBegin_date())) {

                nearestLive = live;
            }
        }
        
        if (nearestLive != null) {
        	if (nearestLive != null) {
        	    System.out.println("対象ライブID=" + nearestLive.getId());
        	    System.out.println("create_flag=" + nearestLive.isCreate_flag());
        	}
            System.out.println("対象ライブID=" + nearestLive.getId());
            System.out.println("対象ライブ名=" + nearestLive.getName());
            System.out.println("開始=" + nearestLive.getBegin_date());
            System.out.println("終了=" + nearestLive.getEnd_date());
        }
        
     // タイムテーブル未作成ならライブなし扱い
        if (nearestLive == null || !nearestLive.isCreate_flag()) {

            request.setAttribute("noLive", true);

            request.setAttribute("band_infos", new ArrayList<BandInfo>());
            request.setAttribute("prepar_infos", new ArrayList<PreparInfo>());
            request.setAttribute("live_info", null);

            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("/WEB-INF/jsp/home_staff.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // ★ 管理者（-1）とスタッフ（1）は全バンドを取得
        if (type == -1 || type >= 1 ) {
            biList = biDao.showAllBands();
        } else {
            // ★ 出演者は自分のバンドだけ
            biList = biDao.showBand(userId);
        }
        
        
        

     // ★ バンドに紐づく準備情報を取得
        List<PreparInfo> piList = new ArrayList<>();

        System.out.println("=== BandInfo List ===");

        List<BandInfo> filteredBands = new ArrayList<>();

        for (BandInfo band : biList) {

            List<PreparInfo> list =
                    piDao.selectByBandId(band.getId());

            List<PreparInfo> targetList =
                    new ArrayList<>();

            System.out.println("piList件数=" + piList.size());
            
            for (PreparInfo pi : list) {

                System.out.println(
                    "bandId=" + band.getId() +
                    ", liveId=" + pi.getLiveInfoId()
                );

                if (nearestLive != null &&
                    pi.getLiveInfoId() == nearestLive.getId()) {

                    targetList.add(pi);
                }
            }

            // このライブに出演するバンドだけ残す
            if (!targetList.isEmpty()) {

                filteredBands.add(band);

                request.setAttribute(
                        "prepar_info_" + band.getId(),
                        targetList);

                piList.addAll(targetList);
            }
        }

        biList = filteredBands;

        // ★ ライブ情報（存在するものを探す）
        LiveInfo liveInfo = nearestLive;

        // ★ 各バンドの曲情報
        for (int i = 0; i < biList.size(); i++) {
            BandInfo band = biList.get(i);
            List<EachMusic> musicList = emDao.select(band.getId());
            request.setAttribute("each_music[" + i + "]", musicList);
        }

        // ★ noData 判定（liveInfo が null でも準備情報は表示する）
        boolean noData = (biList.isEmpty() || piList.isEmpty());
        request.setAttribute("noData", noData);

        // JSP に渡す
        request.setAttribute("band_infos", biList);
        request.setAttribute("prepar_infos", piList);
        request.setAttribute("live_info", liveInfo);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/home_staff.jsp");
        dispatcher.forward(request, response);
    }
    
    
}
