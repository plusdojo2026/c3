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
<link rel="icon" type="image/png" href="image/liveicon.png">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/table_show.css">
</head>

<body>
	<header class="header">
		<div id="nav-wrapper" class="nav-wrapper">
			<div class="hamburger" id="js-hamburger">
				<span class="hamburger__line hamburger__line--1"></span> <span
					class="hamburger__line hamburger__line--2"></span> <span
					class="hamburger__line hamburger__line--3"></span>
			</div>
			<nav class="sp-nav">
				<ul>
					<li>
						<form action="HomeAdminServlet" method="get">
							<button type="submit">ホーム</button>
						</form>
					</li>
					<li>
						<form action="AdminRegistServlet" method="get">
							<button type="submit">新規登録</button>
						</form>
					</li>
					<li>
						<form action="LoginServlet" method="post">
							<button type="submit">ログアウト</button>
						</form>
					</li>
					<li>
						<form action="LiveCreateServlet" method="get">
							<button type="submit">ライブ情報作成</button>
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
		<button type="button" class="delete-schedule-btn" onclick="deleteTable()" >✖</button>
		<h2 class="schedule-title">タイムスケジュール</h2>
		<h3 class="schedule-title"><c:out value="${live_info.name }" /></h3>
		<input type="hidden" name="live_info_id" value="${live_info.id }" id="live_info_id">
		<div id="schedule"></div>
	</div>
	<div id="modal" class="modal">
		<div class="modal-content">

			<span class="close" onclick="closeModal()">×</span>

			<div id="modalBody"></div>

		</div>
	</div>
	<script>

const bands = [

<%List<BandInfo> biList = (List<BandInfo>) request.getAttribute("band_infos");

List<PreparInfo> piList = (List<PreparInfo>) request.getAttribute("prepar_infos");

for (int i = 0; i < biList.size(); i++) {%>

{
    name : "<%=biList.get(i).getName()%>",
    playTime : <%=piList.get(i).getTime()%>,
	changeTime : <%=piList.get(i).getPreparTime()%>,
    id: <%= piList.get(i).getId()%>
}

<%=i < biList.size() - 1 ? "," : ""%>

<%}%>

];

const bandDetails = {

<%for (int i = 0; i < biList.size(); i++) {

	BandInfo band = biList.get(i);

	List<EachMusic> emList = (List<EachMusic>) request.getAttribute("each_music[" + i + "]");
	if (emList == null) {
		emList = new ArrayList<EachMusic>();
	}%>

"<%=band.getName()%>" : [

<%for (int j = 0; j < emList.size(); j++) {

	EachMusic em = emList.get(j);%>

{
    song : "<%=em.getName()%>",
    light : "<%=em.getLightReq()%>",
    sound : "<%=em.getSe()%>",
    note : "<%=em.getMemo()%>"
}

<%=j < emList.size() - 1 ? "," : ""%>

<%}%>

]

<%=i < biList.size() - 1 ? "," : ""%>

<%}%>

};

// ページ移動の際に自動でデータを保存する
window.addEventListener("pagehide", function() {
    const params = new URLSearchParams();
    
    // 順番通りにidを取得する
    bands.forEach(band => {
        params.append("prepar_info_id", band.id);
    });
    params.append("action_type", "edit");
    
    // doPostへ送信する
    navigator.sendBeacon("TableShowServlet", params);
});

</script>
	<script
		src="https://cdn.jsdelivr.net/npm/sortablejs@1.15.6/Sortable.min.js"></script>
	<script src="javascript/common.js"></script>
	<script src="javascript/table_show.js"></script>

</body>

</html>