<%@ page import="java.util.List"%>
<%@ page import="dto.BandInfo"%>
<%@ page import="dto.PreparInfo"%>
<%@ page import="dto.LiveInfo"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
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
						<form action="PreparRegistServlet" method="get">
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
	
		<%
// 開始日時をHH:mmの形に整える
String startTimeStr = "";
String endTimeStr = "";
String startHour = "";
String startMin = "";
if (request.getAttribute("live_info") != null) {
	Object li = request.getAttribute("live_info");
	
	try {
		java.time.LocalDateTime beginDate = (java.time.LocalDateTime) li.getClass().getMethod("getBegin_date").invoke(li);
		java.time.LocalDateTime endDate = (java.time.LocalDateTime) li.getClass().getMethod("getEnd_date").invoke(li);		
		if (beginDate != null) {
			java.time.format.DateTimeFormatter hourformatter = java.time.format.DateTimeFormatter.ofPattern("HH");
			java.time.format.DateTimeFormatter minformatter = java.time.format.DateTimeFormatter.ofPattern("mm");
			startHour = beginDate.format(hourformatter);
			startMin = beginDate.format(minformatter);
		}
    } catch (Exception e) {
        e.printStackTrace();
    }
}
%>
<input type="hidden" id="begin_date" value=<%=startTimeStr%>> 
<input type="hidden" id="begin_date_hour" value=<%=startHour%>> 
<input type="hidden" id="begin_date_min" value=<%=startMin%>> 
	
	<div class="box">
		<h2 class="schedule-title">タイムスケジュール</h2>
		<h3 class="schedule-name"><c:out value="${live_info.name}" /></h3>
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