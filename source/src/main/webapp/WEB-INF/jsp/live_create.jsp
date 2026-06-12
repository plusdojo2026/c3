<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ライブ運営助けるくん</title>
    <link rel="stylesheet" href="css/common.css">
    <link rel="stylesheet" href="css/live_create.css">
</head>
<body>
    <!-- タブ表示 -->
    <!-- ロゴ表示 -->
    <div class="logo">
        <a href="Home"><img src="logo.png" alt=""></a>
    </div>
    <!-- メニュー//タブ -->
    <div id="nav-wrapper" class="nav-wrapper">
    	<div class="hamburger" id="js-hamburger">
    		<span class="hamburger__line hamburger__line--1"></span>
        	<span class="hamburger__line hamburger__line--2"></span>
        	<span class="hamburger__line hamburger__line--3"></span>
    	</div>
    	<nav class="sp-nav">
        <ul>
        	<li><button type="button">新規登録</button></li>
        	<li><button type="button">ライブ情報作成</button></li>
        	<li><button type="button">ログアウト</button></li>
        </ul>
     	</nav>
     	<div class="black-bg" id="js-black-bg"></div>
    </div>

    <!-- メイン -->
    <main>
        <!-- フォーム群 -->

        <form action="" id="live_create">
            <div class="live_name">
                <!-- ライブ名入力 -->
                ライブ名<span class="required"></span><br>
                <input type="text" placeholder="ライブ名" name="live_name">
                </div>
            <div class="set_date">
                <!-- 開始日時入力 -->
                <br>開始日時<span class="required"></span><br>
                <input type="text" placeholder="開始日時" name="begin_date">
                <!-- 終了日時入力 -->
                <br>終了日時<span class="required"></span><br>
                <input type="text" placeholder="終了日時" name="end_date">
            </div>
            <!-- エラーメッセージ表示 -->
            <!-- 出演者(バンド名) & 各バンドの持ち時間 -->
            <div class="band_info">
                <table id="bands_info_row">
                    <tr><th><input type="hidden" name="band_num" value="0">バンド名<span class="required"></span></th><th>持ち時間<span class="required"></span></th><td></td></tr>
                    <tr class="band_info_row">
                        <td><input type="text" name="bandname[0]" placeholder="バンド名"></td>
                        <td><input type="text" name="time[0]" placeholder="持ち時間"></td>
                        <td>
                            <button type="button" onclick="addRow()"><img src="img/plus.svg" alt=""></button>
                            <!-- <button type="button" onclick="removeRow(this)"></button> -->
                        </td>
                    </tr>
                    <tr class="band_info_row">
                        <td><input type="text" name="bandname[1]" placeholder="バンド名"></td>
                        <td><input type="text" name="time[1]" placeholder="持ち時間"></td>
                        <td>
                            <button type="button" onclick="addRow()"><img src="img/plus.svg" alt=""></button>
                            <!-- <button type="button" onclick="removeRow(this)"></button> -->
                        </td>
                    </tr>
                </table>
            </div>
            <p id="blank_alert"></p>
            <div class="cancel_create_btn">
                <!-- キャンセルボタン -->
                <a href="Home" class="cancel_btn">キャンセル</a>
                <!-- 送信 -->
                <input type="submit" name="create" id="create" value="送信">
            </div>
        </form>
    </main>

    <script src="javascript/common.js"></script>
    <script src="javascript/live_create.js"></script>
</body>
</html>