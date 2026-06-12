<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <!-- 共通デザイン（ハンバーガーメニューなど）を読み込む -->
        <link rel="stylesheet" href="common.css">
        <link rel="stylesheet" href="prepar_regist.css">
        <title>ライブ運営助けるくん</title>
        <script src="common.js"></script>
        <script src="prepar_regist.js"></script>

    </head>

    <body>

        <!-- 変えない -->
        <header class="header">
            <div id="nav-wrapper" class="nav-wrapper">
                <div class="hamburger" id="js-hamburger">
                    <span class="hamburger__line hamburger__line--1"></span>
                    <span class="hamburger__line hamburger__line--2"></span>
                    <span class="hamburger__line hamburger__line--3"></span>
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
        <!-- 変えない -->

        <!-- ★ 追加：中央寄せ用のラッパー -->
        <div class="content-wrapper">

            <h1>準備情報登録</h1>
            <hr>


            <!-- ▼▼ 準備情報入力フォーム ▼▼ -->
            <form id="prepar_form" method="POST" action="/c3/PreparRegistServlet">

                <!-- 基本情報入力 -->
                <div class="basic-info">

                    <label>ライブ名</label>
                    <input type="text" name="liveName" value="${liveName}">

                    <label>日程</label>
                    <input type="text" name="date" value="${date}">

                    <label>時間</label>
                    <input type="text" name="time" value="${time}">

                    <label>持ち時間</label>
                    <input type="text" name="playTime" value="${playTime}">

                    <label>バンド名</label>
                    <input type="text" name="band" value="${band}">

                    <label>順番希望</label>
                    <input type="text" name="reqOrder">

                    <label class="required">入場曲</label>
                    <input type="text" name="entranceMusic">

                </div>


                <hr>
                <h2>曲情報</h2>

                <!-- 曲セット追加ボタン -->
                <button type="button" id="addSetBtn">追加 </button>

                <!-- 曲セットが追加される領域 -->
                <div id="musicSets"></div>

                <p id="error_message" style="color:red;"></p>

                <input type="reset" value="キャンセル">
                <input type="submit" value="送信">
            </form>

        </div>
        <!-- ▲▲ フォームここまで ▲▲ -->

    </body>

    </html>