<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
					<li><button type="button">新規登録</button></li>
                    <li><button type="button">ログアウト</button></li>
                    <li><button type="button">ライブ情報作成</button></li>
				</ul>
			</nav>
			<div class="black-bg" id="js-black-bg"></div>
		</div>
		<div class="logo">
			<img src="image/c3-logo.png" alt="ロゴ">
		</div>
	</header>
	<div class="box">
		<button class="delete-schedule-btn">✖</button>
		<h2 class="schedule-title">タイムスケジュール</h2>
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
changeTime : <%=piList.get(i).getPreparTime()%>
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

</script>
	<script
		src="https://cdn.jsdelivr.net/npm/sortablejs@1.15.6/Sortable.min.js"></script>
	<script src="javascript/common.js"></script>
	<script src="javascript/table_show.js"></script>

</body>

</html>