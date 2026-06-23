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
    <link rel="stylesheet" href="css/home_staff.css">
</head>

<body>
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
            <div class="schedule-item">
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
	<script>

<%
List<BandInfo> biList =
    (List<BandInfo>) request.getAttribute("band_infos");
if (biList == null) biList = new ArrayList<>();

List<PreparInfo> piList =
    (List<PreparInfo>) request.getAttribute("prepar_infos");
if (piList == null) piList = new ArrayList<>();
%>

const bands = [
<%
for (int i = 0; i < biList.size(); i++) {
%>
{
    name: "<%= biList.get(i).getName() %>",
    playTime: <%= (i < piList.size() ? piList.get(i).getTime() : 0) %>,
    changeTime: <%= (i < piList.size() ? piList.get(i).getPreparTime() : 0) %>
}<%= (i < biList.size() - 1) ? "," : "" %>
<%
}
%>
];

const bandDetails = {
<%
for (int i = 0; i < biList.size(); i++) {

    BandInfo band = biList.get(i);

    List<EachMusic> emList =
        (List<EachMusic>) request.getAttribute("each_music[" + i + "]");

    if (emList == null) {
        emList = new ArrayList<>();
    }
%>
"<%= band.getName() %>": [
<%
    for (int j = 0; j < emList.size(); j++) {
        EachMusic em = emList.get(j);
%>
{
    song: "<%= em.getName() %>",
    light: "<%= em.getLightReq() %>",
    sound: "<%= em.getSe() %>",
    note: "<%= em.getMemo() %>"
}<%= (j < emList.size() - 1) ? "," : "" %>
<%
    }
%>
]<%= (i < biList.size() - 1) ? "," : "" %>
<%
}
%>
};

</script>
   <!-- <script src="https://cdn.jsdelivr.net/npm/sortablejs@1.15.6/Sortable.min.js"></script>-->
    <script src="javascript/common.js"></script>
    <script src="javascript/home_staff.js"></script>

</body>

</html>