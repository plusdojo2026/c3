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


<script src="javascript/table_create.js"></script>

</body>
</html>

