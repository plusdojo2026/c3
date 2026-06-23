<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<div class="received-data-box">
    <h2>ライブ情報</h2>
    <p>ライブ名：${live_info.name}</p>
    <p>開催日：${live_info.begin_date}</p>


    <h2>出演バンド一覧</h2>
    <c:forEach var="band" items="${band_infos}">
        <div class="band-box">
            <p>バンド名：${band.band_name}</p>
            <p>持ち時間：${band.time} 分</p>
        </div>
    </c:forEach>

    <h2>準備情報</h2>
    <c:forEach var="prep" items="${prepar_infos}">
        <div class="prep-box">
            <p>バンドID：${prep.bandInfoId}</p>
            <p>準備内容：${prep.prepar_info}</p>
        </div>
    </c:forEach>

    <h2>曲情報</h2>
    <c:forEach var="prep" items="${prepar_infos}" varStatus="i">
        <h3>${band_infos[i.index].band_name} の曲一覧</h3>
        <c:forEach var="music" items="${requestScope['each_music[' += i.index += ']']}">
            <p>曲名：${music.music_name}</p>
        </c:forEach>
    </c:forEach>
</div>


<!-- 転換時間設定 -->
<div class="convert-setting">
    <label>転換時間：</label>
    <input type="number" id="convertTime" value="10" min="0"> 分
    <button onclick="applyConvertTime()">設定</button>
</div>

<!-- メインのタイムテーブル作成ボックス -->
<div class="box">
    <h2>${live_info.name}</h2>

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
    <input type="time" id="startTime" value="10:00">
    <button onclick="applyConvertTime()">設定</button>
</div>

<!-- モーダル -->
<div id="modal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <div id="modalBody"></div>
    </div>
</div>


<script>
    // バンド情報（List<BandInfo>）
    const bandInfos = [
        <c:forEach var="b" items="${band_infos}">
            {
                id: ${b.id},
                name: "${b.band_name}",
                time: ${b.time}
            },
        </c:forEach>
    ];

    // 準備情報（List<PreparInfo>）
    const preparInfos = [
        <c:forEach var="p" items="${prepar_infos}">
            {
                bandId: ${p.bandInfoId},
                info: "${p.prepar_info}"
            },
        </c:forEach>
    ];

</script>

<script src="javascript/common.js"></script>
<script src="javascript/table_create.js"></script>
</body>
</html>

