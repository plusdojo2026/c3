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

        // ★ 管理者（-1）とスタッフ（1）は全バンドを取得
        if (type == -1 || type == 1) {
            biList = biDao.showAllBands();
        } else {
            // ★ 出演者は自分のバンドだけ
            biList = biDao.showBand(userId);
        }

        // バンドに紐づく準備情報
        List<PreparInfo> piList = new ArrayList<>();
        for (BandInfo band : biList) {
            piList.addAll(piDao.selectByBandId(band.getId()));
        }

        // ライブ情報（最初の1件）
        LiveInfo liveInfo = null;
        if (!piList.isEmpty()) {
            liveInfo = liDao.select(piList.get(0).getLiveInfoId());
        }

        // 各バンドの曲情報
        for (int i = 0; i < biList.size(); i++) {
            BandInfo band = biList.get(i);
            List<EachMusic> musicList = emDao.select(band.getId());
            request.setAttribute("each_music[" + i + "]", musicList);
        }

        // データが存在しないかチェック
        boolean noData = (biList.isEmpty() || piList.isEmpty() || liveInfo == null);
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
