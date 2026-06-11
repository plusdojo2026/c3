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
        	<li><button type="button">ログアウト</button></li>
        	<li><button type="button">バンド情報</button></li>
        	<li><button type="button">ライブ情報</button></li>
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
                <c:if test="${not empty band_info }">
                <input type="hidden" name="band_id" value=<c:out value="${band_info.id }" />>
                <input type="text" placeholder="バンド名" name="band_name" value=<c:out value="${band_info.name }" />>
                </c:if>
                <c:if test="${empty band_info }">
                <input type="text" placeholder="バンド名" name="band_name" value=<c:out value="${band_info.name }" />>
                </c:if>

            </div>
            <!-- バンドメンバーの名前 & 役目 -->
            <div class="band_info">
                <table id="members_info_row">
                    <tr><th><input type="hidden" name="member_num" value="0">メンバー名<span class="required"></span></th><th>担当<span class="required"></span></th><td></td></tr>
                    <tr class="band_info_row">
                    <c:if test="${not empty band_members }">
                    <c:forEach var="band_member" items="${band_members }">
                        <td><input type="text" name="member_name[0]" placeholder="氏名" value="${band_member.name }"></td>
                        <td>
                        <c:if test= "${not empty parts }">
                        <select name="parts[0]" id="parts">
                            <option value="">--選択してください--</option>
                            <c:forEach var="part" items=<c:out value="${parts }" />>
                            	<option value=<c:out value="${part.id }" /> <c:if test="${{part.id } == ${band_member.partId }}">selected</c:if>><c:out value="${part.name }" /></option>
                            </c:forEach>
                        </select></c:if></td>
                        <td>
                            <button type="button" onclick="addRow()"><img src="img/plus.svg" alt=""></button>
                            <!-- <button type="button" onclick="removeRow(this)"></button> -->
                        </td></c:forEach></c:if>
					<c:if test="${empty band_members }">
                        <td><input type="text" name="member_name[0]" placeholder="氏名"></td>
                        <td>
                        <c:if test= "${not empty parts }">
                        <select name="parts[0]" id="parts">
                            <option value="">--選択してください--</option>
                            <c:forEach var="part" items=<c:out value="${parts }" />>
                            	<option value=<c:out value="${part.id }" /> <c:if test="${{part.id } == ${band_member.partId }}">selected</c:if>><c:out value="${part.name }" /></option>
                            </c:forEach>
                        </select></c:if></td>
                        <td>
                            <button type="button" onclick="addRow()"><img src="img/plus.svg" alt=""></button>
                            <!-- <button type="button" onclick="removeRow(this)"></button> -->
                        </td>
					</c:if>
                    </tr>
                </table>
            </div>
            <div class="cancel_create_btn">
                <!-- キャンセルボタン -->
                <a href="HomeServlet" class="cancel_btn">キャンセル</a>
                <!-- 登録 -->
                <input type="submit" name="create" id="create" value="登録">
            </div>
        </form>
    </main>

    <script src="javascript/common.js"></script>
    <script src="javascript/mod_band.js"></script>
</body>
</html>