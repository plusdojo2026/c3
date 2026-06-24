<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page import="java.util.List"%>
<%@ page import="dto.BandInfo"%>
<%@ page import="dto.PreparInfo"%>
<%@ page import="dto.EachMusic"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="ja">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>タイムテーブル表示</title>
    <link rel="stylesheet" href="css/common.css">
    <link rel="stylesheet" href="css/home_band.css">
</head>

<body>

<!-- 	<c:if test="${noData}">	
		<script>
    		alert("表示できるデータがありません。");
		</script>
	</c:if> -->

    <header class="header">
        <div id="nav-wrapper" class="nav-wrapper">
            <div class="hamburger" id="js-hamburger">
                <span class="hamburger__line hamburger__line--1"></span>
                <span class="hamburger__line hamburger__line--2"></span>
                <span class="hamburger__line hamburger__line--3"></span>
            </div>
            <nav class="sp-nav">
                <ul>
                    <li>
						<form action="LoginServlet" method="post">
							<button type="submit">ログアウト</button>
						</form>
					</li>
					<li>
						<form action="ModBandServlet" method="get">
							<button type="submit">バンド情報</button>
						</form>
					</li>

					<li>
						<form action="LiveShowServlet" method="get">
							<button type="submit">ライブ一覧</button>
						</form>
					</li>
                </ul>
            </nav>
            <div class="black-bg" id="js-black-bg"></div>
        </div>
        <div class="logo">
            <img src="image/c3-logo.png" alt="ロゴ">
        </div>
    </header>
    <div class="box">
        <h2 class="schedule-title">タイムスケジュール</h2>
        <c:if test="${not empty live_info}">
        <h3>${live_info.name}</h3>
        <p>${live_info.begin_date}</p>
    </c:if>
        <div id="schedule">
         <c:forEach var="pi" items="${prepar_infos}">
            <div class="schedule-item" onclick="showBandDetail(${pi.bandInfoId})">

                <h4>${pi.setlist}番目</h4>
                <p>持ち時間：${pi.time}分</p>
				<p>準備時間：${pi.preparTime}分</p>
				<p>準備項目：${pi.preparItems}</p>
				<p>入場曲：${pi.entranceMusic}</p>
			</div>
         </c:forEach>
    </div>
    <div id="modal" class="modal">
    <div class="modal-content">

        <span class="close" onclick="closeModal()">×</span>

        <div id="modalBody"></div>
	</div>
    </div>
    </div>
<script>
<%
List<BandInfo> biList =
    (List<BandInfo>) request.getAttribute("band_infos");
if (biList == null) biList = new ArrayList<>();
%>

const bandPreparInfos = {
		<%
		List<BandInfo> biList2 = (List<BandInfo>) request.getAttribute("band_infos");
		for (BandInfo band : biList2) {
		    List<PreparInfo> list = (List<PreparInfo>) request.getAttribute("prepar_info_" + band.getId());
		
		    
		    System.out.println(
		        "JSP bandId="
		        + band.getId()
		        + " list="
		        + list
		    );
		 
		%>
		    "<%= band.getId() %>": [
		    <%
		        if (list != null) {
		            for (int j = 0; j < list.size(); j++) {
		                PreparInfo pi = list.get(j);
		    %>
		        {
		            time: <%= pi.getTime() %>,
		            preparTime: <%= pi.getPreparTime() %>,
		            preparItems: "<%= pi.getPreparItems() %>",
		            entranceMusic: "<%= pi.getEntranceMusic() %>"
		        }<%= (j < list.size() - 1) ? "," : "" %>
		    <%
		            }
		        }
		    %>
		    ]<%= (biList2.indexOf(band) < biList2.size() - 1) ? "," : "" %>
		<%
		}
		%>
		};
		console.log("bandPreparInfos:", bandPreparInfos);

		const bands = [
			<%
			for (BandInfo band : biList) {
			    List<PreparInfo> list = (List<PreparInfo>) request.getAttribute("prepar_info_" + band.getId());
			    int play = 0;
			    int change = 0;

			    if (list != null && !list.isEmpty()) {
			        play = list.get(0).getTime();
			        change = list.get(0).getPreparTime();
			    }
			%>
			    {
			        id: <%= band.getId() %>,
			        name: "<%= band.getName() %>",
			        playTime: <%= play %>,
			        changeTime: <%= change %>
			    }<%= (biList.indexOf(band) < biList.size() - 1) ? "," : "" %>
			<%
			}
			%>
			];


console.log("bandPreparInfos:", bandPreparInfos);
</script>


   <!-- <script src="https://cdn.jsdelivr.net/npm/sortablejs@1.15.6/Sortable.min.js"></script>-->
    <script src="javascript/common.js"></script>
    <script src="javascript/home_band.js"></script>

</body>

</html>