<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ライブ運営助けるくん</title>
<link rel = "stylesheet" href="${pageContext.request.contextPath}/css/common.css">
<link rel = "stylesheet" href="${pageContext.request.contextPath}/css/home_admin.css">
</head>

<body>
    <header>
    <div class="logo">
			<img src="image/c3-logo.png" alt="ロゴ">
		</div>
    <div class="container"> 

<div id="nav-wrapper" class="nav-wrapper">
        <div class="hamburger" id="js-hamburger">
            <span class="hamburger__line hamburger__line--1"></span>
            <span class="hamburger__line hamburger__line--2"></span>
            <span class="hamburger__line hamburger__line--3"></span>
        </div>
        <nav class="sp-nav">
            <ul>
             <li><button type="button" href="MenuServlet">ホーム</button></li>
                <li><button type="button" href="AdminRegistServlet">新規登録</button></li>
                <li><button type= "button" href="LogingServlet" >ログアウト</button></li>
                <li><button type="button" href="LiveCreateServlet" >ライブ情報作成</button></li>
            </ul>
        </nav>
        <div class="black-bg" id="js-black-bg"></div>
        </div>
    </div>
    </header>
 
<c:if test = "${not empty lives}">

<c:forEach var = "live" items="${lives}">

<form action="/c3/HomeAdminServlet"	method = "POST">
<input type = "submit" value = "${live.begin_date}" class = "date">
<input type= "hidden" value = "${live.id}" name = "test" >
</form>
</c:forEach>
</c:if>

<c:if test = "${noLiveInfo}">
<div class = "center-message">
ライブ情報作成画面からライブ情報を登録してください
</div>
</c:if>

<script>
const noEntranceMusic = ${noEntranceMusic ? "true" : "false"};
</script>


<script src="javascript/common.js"></script>
<script src="javascript/home_admin.js"></script>
</body>

</html>