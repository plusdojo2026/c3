<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    
   <header class="header">
        <div id="nav-wrapper" class="nav-wrapper">
            <div class="hamburger" id="js-hamburger">
                <span class="hamburger__line hamburger__line--1"></span>
                <span class="hamburger__line hamburger__line--2"></span>
                <span class="hamburger__line hamburger__line--3"></span>
            </div>
            <nav class="sp-nav">
                <ul>
                    <li><form action="LoginServlet" method="GET">
                    <button type="submit">ログアウト</button>
                    </form></li>
                    <li><form action="ModBandServlet" method="GET">
                    <button type="submit">バンド情報</button>
                    </form></li>
                    <li><form action="LiveShowServlet" method="GET">
                    <button type="submit">ライブ情報</button>
                    </form></li>
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

        <p id="blank_alert" class="error"></p>
        <form action="/c3/ModBandServlet" method="post" id="band_member">
            <div class="band_name">
                バンド名<span class="required"></span><br>
                <c:if test="${not empty band_info_id }">
                <input type="hidden" name="band_id" value=<c:out value="${band_info_id }" />>
                <input type="text" placeholder="バンド名" name="band_name" value=<c:out value="${band_info_name }" />>
                </c:if>
                <c:if test="${empty band_info_id }">
                <input type="hidden" name="band_id" value="0" >
                <input type="text" placeholder="バンド名" name="band_name" value=<c:out value="${band_info_name }" />>
                </c:if>

            </div>
            <!-- バンドメンバーの名前 & 役目 -->
            <div class="band_info">
                <table id="members_info_row">
                    <tr><th><input type="hidden" name="member_num" value="0">メンバー名<span class="required"></span></th><th>担当<span class="required"></span></th>
                    <td><button type="button" onclick="addRow()"><img src="image/plus.svg" alt=""></button></td></tr>
                    
                    <!-- 既に登録されたデータがある場合 -->
                    <c:if test="${not empty band_members }">
                    <c:forEach var="band_member" items="${band_members }" varStatus="status">
                    <tr class="band_info_row">
                        <td>
                        <input type="hidden" name="member_id[${status.index }]" value=<c:out value="${band_member.id }" />>
                        <input type="text" name="member_name[${status.index }]" placeholder="氏名" value="${band_member.name }"></td>
                        <td>
                        <c:if test= "${not empty parts }">
                        <select name="parts[0]" >
                            <option value="">--選択してください--</option>
                            <c:forEach var="part" items="${parts }">
                            	<option value=<c:out value="${part.id }" /> <c:if test="${part.id  == band_member.partId }">selected</c:if>><c:out value="${part.name }" /></option>
                            </c:forEach>
                        </select></c:if></td>
                        <td>
                            <button type="button" onclick="addRow()"><img src="image/plus.svg" alt=""></button>
                            <button type="button" onclick="removeRow(this)"><img src="image/delete.svg" alt=""></button>
                        </td></tr></c:forEach></c:if>
                        
                    <!-- 既に入力されているデータが無い場合 -->
					<c:if test="${empty band_members }">
					<tr class="band_info_row">
                        <td>
                        <input type="hidden" name="member_id[0]" value="0">
                        <input type="text" name="member_name[0]" placeholder="氏名"></td>
                        <td>
                        <c:if test= "${not empty parts }">
                        <select name="parts[0]" >
                            <option value="">--選択してください--</option>
                            <c:forEach var="part" items="${parts }">
                            	<option value=<c:out value="${part.id }" />><c:out value="${part.name }" /></option>
                            </c:forEach>
                        </select></c:if></td>
                        <td>
                            <button type="button" onclick="addRow()"><img src="image/plus.svg" alt=""></button>
                            <button type="button" onclick="removeRow(this)"><img src="image/delete.svg" alt=""></button>
                        </td>
                    </tr>
                    <tr class="band_info_row">
                        <td>
                        <input type="hidden" name="member_id[1]" value="0">
                        <input type="text" name="member_name[1]" placeholder="氏名"></td>
                        <td>
                        <c:if test= "${not empty parts }">
                        <select name="parts[1]" >
                            <option value="">--選択してください--</option>
                            <c:forEach var="part" items="${parts }">
                            	<option value=<c:out value="${part.id }" />><c:out value="${part.name }" /></option>
                            </c:forEach>
                        </select></c:if></td>
                        <td>
                            <button type="button" onclick="addRow()"><img src="image/plus.svg" alt=""></button>
                            <button type="button" onclick="removeRow(this)"><img src="image/delete.svg" alt=""></button>
                        </td>
                    </tr>
                    <tr class="band_info_row">
                        <td>
                        <input type="hidden" name="member_id[2]" value="0">
                        <input type="text" name="member_name[2]" placeholder="氏名"></td>
                        <td>
                        <c:if test= "${not empty parts }">
                        <select name="parts[2]" >
                            <option value="">--選択してください--</option>
                            <c:forEach var="part" items="${parts }">
                            	<option value=<c:out value="${part.id }" />><c:out value="${part.name }" /></option>
                            </c:forEach>
                        </select></c:if></td>
                        <td>
                            <button type="button" onclick="addRow()"><img src="image/plus.svg" alt=""></button>
                            <button type="button" onclick="removeRow(this)"><img src="image/delete.svg" alt=""></button>
                        </td>
                    </tr>
					</c:if>
					
					<!-- JavaScriptで繰り返す用のひな形 -->
					<template id="band_row_template">
					<tr class="band_info_row">
						<td>
                        <input type="hidden" name="member_id_temp" value="0">
                        <input type="text" name="membername_temp" placeholder="氏名"></td>
                        <td>
                        <c:if test= "${not empty parts }">
                        <select name="parts_temp" >
                            <option value="">--選択してください--</option>
                            <c:forEach var="part" items="${parts }">
                            	<option value=<c:out value="${part.id }" />><c:out value="${part.name }" /></option>
                            </c:forEach>
                        </select></c:if></td>
                        <td>
                            <button type="button" onclick="addRow()"><img src="image/plus.svg" alt=""></button>
                            <button type="button" onclick="removeRow(this)"><img src="image/delete.svg" alt=""></button>
                        </td>
                    </tr>                        
					</template>
                </table>
            </div>
            <div class="cancel_create_btn">
                <!-- キャンセルボタン -->
                <a href="c3/HomeAdminServlet" class="cancel_btn">キャンセル</a>
                <!-- 登録 -->
                <input type="submit" name="create" id="create" value="登録">
            </div>
        </form>
    </main>

    <script src="javascript/common.js"></script>
    <script src="javascript/mod_band.js" ></script>
</body>
</html>