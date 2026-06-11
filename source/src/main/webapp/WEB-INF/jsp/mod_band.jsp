<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ライブ運営助けるくん</title>
    <link rel="stylesheet" href="css/common.css">
    <link rel="stylesheet" href="css/mod_band.css">
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
        	<li><button type="button">バンド情報</button></li>
        	<li><button type="button">ライブ情報</button></li>
        	<li><button type="button">ログアウト</button></li>
        </ul>
     	</nav>
     	<div class="black-bg" id="js-black-bg"></div>
    </div>

    <!-- メイン -->
    <main>
        <!-- フォーム群 -->

        <p id="blank_alert" class="error"></p>
        <form action="" id="band_member">
            <div class="band_name">
                バンド名<span class="required"></span><br>
                <input type="text" placeholder="バンド名" name="band_name">
            </div>
            <!-- 出演者(バンド名) & 各バンドの持ち時間 -->
            <div class="band_info">
                <table id="members_info_row">
                    <tr><th><input type="hidden" name="member_num" value="0">メンバー名<span class="required"></span></th><th>担当<span class="required"></span></th><td></td></tr>
                    <tr class="band_info_row">
                        <td><input type="text" name="member_name[0]" placeholder="氏名"></td>
                        <td><select name="parts[0]" id="parts">
                            <option value="">--選択してください--</option>
                            <option value="1">a</option>
                        </select></td>
                        <td>
                            <button type="button" onclick="addRow()"><img src="img/plus.svg" alt=""></button>
                            <!-- <button type="button" onclick="removeRow(this)"></button> -->
                        </td>
                    </tr>
                </table>
            </div>
            <div class="cancel_create_btn">
                <!-- キャンセルボタン -->
                <a href="Home" class="cancel_btn">キャンセル</a>
                <!-- 登録 -->
                <input type="submit" name="create" id="create" value="登録">
            </div>
        </form>
    </main>

    <script src="javascript/common.js"></script>
    <script src="javascript/mod_band.js"></script>
</body>
</html>