<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <!-- メイン -->
    <main>
        <!-- フォーム群 -->

        <form action="/c3/LiveCreateServlet" method="POST" id="live_create">
            <div class="live_name">
                <!-- ライブ名入力 -->
                ライブ名<span class="required"></span><br>
                <input type="text" placeholder="ライブ名" name="live_name">
                </div>
            <div class="set_date">
                <!-- 開始日時入力 -->
                <br>開始日時<span class="required"></span><br>
                <input type="datetime-local" placeholder="開始日時" name="begin_date">
                <!-- 終了日時入力 -->
                <br>終了日時<span class="required"></span><br>
                <input type="datetime-local" placeholder="終了日時" name="end_date">
            </div>
            <!-- エラーメッセージ表示 -->
            <!-- 出演者(バンド名) & 各バンドの持ち時間 -->
            <div class="band_info">
                <table id="bands_info_row">
                    <tr><th><input type="hidden" name="band_num" value="0">バンド名<span class="required"></span></th><th>持ち時間<span class="required"></span></th>
                    <td><button type="button" onclick="addRow()"><img src="image/plus.svg" alt=""></button></td></tr>
                    <tr class="band_info_row">
                    <!-- バンド名入力をselectでできるようにしたい -->
                        <td>
                        <c:if test="${not empty band_infos }">
                        	<select name="band_infos[0]">
                        		<c:forEach var="band_info" items="${band_infos }">
                        			<option value=<c:out value="${band_infos.id }" />><c:out value="${band_infos.name }" /></option>
                        		</c:forEach>
                        	</select>
                        </c:if>
                        <c:if test="${empty band_infos }">
                        	<select name="band_infos[0]" >
                        		<option value="0">バンド情報がありません</option>
                        	</select>
                        </c:if>
                        <td><input type="text" name="time[0]" placeholder="持ち時間"></td>
                        <td>
                            <button type="button" onclick="addRow()"><img src="image/plus.svg" alt=""></button>
                            <button type="button" onclick="removeRow(this)"><img src="image/delete.svg" alt=""></button>
                        </td>
                    </tr>
                    <tr class="band_info_row">
                        <td><input type="text" name="bandname[1]" placeholder="バンド名"></td>
                        <td><input type="text" name="time[1]" placeholder="持ち時間"></td>
                        <td>
                            <button type="button" onclick="addRow()"><img src="image/plus.svg" alt=""></button>
                            <button type="button" onclick="removeRow(this)"><img src="image/delete.svg" alt=""></button>
                        </td>
                    </tr>
                </table>
            </div>
            <p id="blank_alert" class="error"></p>
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