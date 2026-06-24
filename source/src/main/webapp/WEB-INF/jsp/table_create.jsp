<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タイムテーブル作成</title>

<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/table_create.css">

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
<h1 style="text-align:center;">タイムテーブル作成</h1>

<!-- 受け取ったデータ表示 -->
<%-- <div class="received-data-box">
    <h2>ライブ情報</h2>
    <p>ライブ名：<c:out value="${live_info.name}" /></p>
    <p>開催日：<c:out value="${live_info.begin_date}" /></p>


    <h2>出演バンド一覧</h2>
    <c:forEach var="band" items="${band_infos}" varStatus="i">
        <div class="band-box">
            <p>バンド名：<c:out value="${band.name}" /></p>
            <p>持ち時間：<c:out value="${prepar_infos[i.index].time}" /> 分</p>
        </div>
    </c:forEach>

    <h2>準備情報</h2>
    <c:forEach var="prep" items="${prepar_infos}">
        <div class="prep-box">
            <p>バンドID：<c:out value="${prep.bandInfoId}" /></p>
            <p>入場曲：<c:out value="${prep.entranceMusic }" /></p>
            <p>準備項目：<c:out value="${prep.preparItems}" /></p>
        </div>
    </c:forEach>

    <h2>曲情報</h2>
    <c:forEach var="prep" items="${prepar_infos}" varStatus="i">
        <h3>${band_infos[i.index].name} の曲一覧</h3>
        <c:forEach var="music" items="${requestScope['each_music[' += i.index += ']']}">
            <p>曲名：${music.name}</p>
        </c:forEach>
    </c:forEach>
</div> --%>

<%
// 開始日時をHH:mmの形に整える
String startTimeStr = "";
String endTimeStr = "";
if (request.getAttribute("live_info") != null) {
	Object li = request.getAttribute("live_info");
	
	try {
		java.time.LocalDateTime beginDate = (java.time.LocalDateTime) li.getClass().getMethod("getBegin_date").invoke(li);
		java.time.LocalDateTime endDate = (java.time.LocalDateTime) li.getClass().getMethod("getEnd_date").invoke(li);		
		if (beginDate != null) {
			java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm");
            startTimeStr = beginDate.format(formatter);
            endTimeStr = endDate.format(formatter);
		}
    } catch (Exception e) {
        e.printStackTrace();
    }
}
%>

<!-- 転換時間設定 -->
<div class="convert-setting">
    <label>転換時間：</label>
    <input type="number" id="convertTime" value="10" min="0" name="time"> 分
    <button onclick="applyConvertTime()">設定</button>
</div>

<!-- メインのタイムテーブル作成ボックス -->
<div class="box">
    <h2><c:out value="${live_info.name }"/><span style="font-size: 16px"> ─ <%=endTimeStr %></span></h2>
    <input type="hidden" id="live_info_id" value="<c:out value="${live_info.id }"/>" />
    <input type="hidden" id="end_date" value="<%=endTimeStr %>">

    <div id="timetableList"
        ondragover="handleTableAreaDragOver(event)"
        ondrop="handleTableAreaDrop(event)">
    </div>
</div>

<div class="info-box">
    <h3>準備情報</h3>

    <div id="prepList" style="width:100%; padding:10px; overflow-y:auto;"></div>

    <button class="btn btn-primary save-btn" onclick="exportTimetable()">保存</button>
    
    <!-- キャンセルボタン -->
	<button class="btn btn-secondary cancel-btn" onclick="cancelPage()">キャンセル</button>

</div>
<!-- ツールチップ -->
<div id="tooltip" class="tooltip"></div>

<!-- 自動計算機能 -->
<div class="convert-setting beginbox">
    <label>開始時刻：</label>
    <input type="time" id="startTime" value="<%= startTimeStr %>">
    <button onclick="applyConvertTime()">設定</button>
</div>

<!-- モーダル -->
<div id="modal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <div id="modalBody"></div>
    </div>
</div>

<script src="javascript/common.js"></script>
<script src="javascript/table_create.js"></script>

<script>
/* サーブレットから受け取ったデータを配列に入れる */

(function(){
	prepBands = [];
    bandDetails = {};
    
	<c:forEach var="pi" items="${prepar_infos}" varStatus="i">
	 	bandDetails["${band_infos[i.index].name}"] = []; 
		bandDetails["${band_infos[i.index].name}"] = [
			<c:forEach var="music" items="${each_music[i.index]}" varStatus="j">
	        { song: "${music.name}", light: "${music.lightReq}", sound: "${music.se}", note: "${music.memo}" }${not j.last ? ',' : ''}
			</c:forEach>
		];

		if (bandDetails["${band_infos[i.index].name}"]) {
			bandDetails["${band_infos[i.index].name}"] = bandDetails["${band_infos[i.index].name}"].filter((music, index, self) =>
				index === self.findIndex((m) => m.song === music.song));
		}
		
		prepBands.push({
			id: "${pi.id }",
			name: "${band_infos[i.index].name}",
		    time: "${pi.time }",
		    item:"${pi.preparItems}",
		    music:"${pi.entranceMusic}" 
		});
	</c:forEach>
	
	console.log("値を代入(b)：", bandDetails);
	console.log("値を代入(p)：", prepBands);
	
	if (typeof renderPrepList === "function") {
		renderPrepList();
	}
	
	if (typeof renderTimetable === "function") {
		renderTimetable();
	}
	
})();


/*     // バンド情報（List<BandInfo>）
    const bandInfos = [
        <c:forEach var="b" items="${band_infos}" varStatus="i">
            {
                id: ${b.id},
                name: "${b.name}",
                time: ${prepar_infos[i.index].time}
            },
        </c:forEach>
    ];

    // 準備情報（List<PreparInfo>）
    const preparInfos = [
        <c:forEach var="p" items="${prepar_infos}">
            {
                bandId: ${p.bandInfoId},
                info: "${p.preparItems}"
            },
        </c:forEach>
    ]; */

</script>

</body>
</html>

