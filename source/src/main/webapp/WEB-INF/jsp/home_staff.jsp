<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>タイムテーブル表示</title>
    <link rel="stylesheet" href="css/common.css">
    <link rel="stylesheet" href="css/home_band.css">
</head>

<body>
    <header class="header">
        <div id="nav-wrapper" class="nav-wrapper">
            <div class="hamburger" id="js-hamburger">
                <span class="hamburger__line hamburger__line--1"></span>
                <span class="hamburger__line hamburger__line--2"></span>
                <span class="hamburger__line hamburger__line--3"></span>
            </div>
            <nav class="sp-nav">
                <ul>
                    <li><button type="button">ログアウト</button></li>
                    <li><button type="button">バンド情報</button></li>
                    <li><button type="button">準備情報</button></li>
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
    <div id="modal" class="modal">
    <div class="modal-content">

        <span class="close" onclick="closeModal()">×</span>

        <div id="modalBody"></div>

    </div>
</div>
    <script src="https://cdn.jsdelivr.net/npm/sortablejs@1.15.6/Sortable.min.js"></script>
    <script src="javascript/common.js"></script>
    <script src="javascript/home_band.js"></script>

</body>

</html>