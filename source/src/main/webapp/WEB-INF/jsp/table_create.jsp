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

<!-- 転換時間設定 -->
<div class="convert-setting">
    <label>転換時間：</label>
    <input type="number" id="convertTime" value="10" min="0"> 分
    <button onclick="applyConvertTime()">設定</button>
</div>

<!-- メインのタイムテーブル作成ボックス -->
<div class="box">
    <h2>タイムテーブル</h2>

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

<script src="javascript/common.js"></script>
<script src="javascript/table_create.js"></script>

</body>
</html>

