<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>ライブ運営助けるくん</title>
<link rel="stylesheet" type="text/css" href="css/table_create.css">
</head>
<body>
<div class="wrapper">
  <!-- ヘッダー（ここから） -->
  <h1 id="logo">
    <a href="home_staff.html"><img src="images/c3-logo.png" width="450" height="110" alt="ライブ運営助けるくん"></a>
  </h1>

     <div class="box">
        <h2>タイムテーブル</h2>
       
        <div class="button">
            <button class="btn btn-secondary">転換</button>
            <button class="btn btn-primary" draggable="true">ヨルシカ</button>
            <button class="btn btn-primary" draggable="true" data-info="ギター2本、ベース1本、ドラム、キーボード">THE ORAL CIGARETTES</button>
            <button class="btn btn-primary" draggable="true" data-info="ギター1本、ベース1本、ドラム">あいみょん</button>
            <button class="btn btn-primary" draggable="true" data-info="アコギ1本、カホン">サザンオールスターズ</button>
        </div>
        
     </div>

     <div class="info-box" id="infobox">
        <h3>準備情報</h3>

        <button class="btn btn-primary" draggable="true">バンド名</button>
        
     </div>

</div>

    <div id="modal" class="modal">
    <div class="modal-content">

        <span class="close" onclick="closeModal()">×</span>

        <div id="modalBody"></div>
        
<script src="js/table_create.js"></script>

<div class="convert-setting">
    <label>転換時間（分）</label>
    <input type="number" id="convertTime" value="10">
    <button id="applyConvert">適用</button>
</div>

</body>
</html>