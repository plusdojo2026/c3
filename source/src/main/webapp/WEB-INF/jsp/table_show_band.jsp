<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="dto.BandInfo"%>
<%@ page import="dto.PreparInfo"%>
<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>タイムテーブル表示</title>
<link rel="icon" type="image/png" href="image/liveicon.png">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/table_show_band.css">
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
						<form action="HomeBandServlet" method="get">
							<button type="submit">ホーム</button>
						</form>
					</li>
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
						<form action="PreparResistServlet" method="get">
							<button type="submit">準備情報</button>
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
		<div id="schedule"></div>
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
</script>
	<script
		src="https://cdn.jsdelivr.net/npm/sortablejs@1.15.6/Sortable.min.js"></script>
	<script src="javascript/common.js"></script>
	<script src="javascript/table_show_band.js"></script>

</body>

</html>