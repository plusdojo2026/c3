<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タイムテーブル作成</title>

<link rel="stylesheet" href="css/table_create.css">

</head>
<body>

<h1 style="text-align:center;">タイムテーブル作成</h1>

<!-- 転換時間設定 -->
<div class="convert-setting">
    <label>転換時間：</label>
    <input type="number" id="convertTime" value="10"> 分
    <button onclick="applyConvertTime()">設定</button>
</div>

<!-- メインのタイムテーブル作成ボックス -->
<div class="box">
    <h2>タイムテーブル</h2>
    <div id="timetableList"></div>
</div>

<!-- 準備情報ボックス -->
<div class="info-box">
    <h3>準備情報</h3>

    <div id="prepList" style="width:100%; padding:10px; overflow-y:auto;">
        <!-- バンド名ボタンがここに並ぶ -->
    </div>

    <button class="btn btn-primary" onclick="exportTimetable()">タイムテーブルを出力</button>
    <button class="btn btn-primary" onclick="clearAll()">全削除</button>
</div>

<!-- ツールチップ -->
<div id="tooltip" class="tooltip"></div>

<script src="javascript/table_create.js"></script>

</body>
</html>
