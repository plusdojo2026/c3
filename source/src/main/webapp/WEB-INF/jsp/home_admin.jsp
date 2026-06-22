<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ライブ運営助けるくん</title>
<link rel="icon" type="image/png" href="image/liveicon.png">
<link rel = "stylesheet" href="${pageContext.request.contextPath}/css/common.css">
<link rel = "stylesheet" href="${pageContext.request.contextPath}/css/home_admin.css">
</head>

<body>

    	<header class="header">
		<div id="nav-wrapper" class="nav-wrapper">
			<div class="hamburger" id="js-hamburger">
				<span class="hamburger__line hamburger__line--1"></span> <span
					class="hamburger__line hamburger__line--2"></span> <span
					class="hamburger__line hamburger__line--3"></span>
			</div>
			<nav class="sp-nav">
				<ul>
					<li>
						<form action="AdminRegistServlet" method="post">
							<button type="submit">新規登録</button>
						</form>
					</li>
					<li>
						<form action="LoginServlet" method="get">
							<button type="submit">ログアウト</button>
						</form>
					</li>

					<li>
						<form action="LiveCreateServlet" method="get">
							<button type="submit">ライブ情報作成</button>
						</form>
					</li>
				</ul>
			</nav>

			<div class="black-bg" id="js-black-bg"></div>
		</div>
		<div class="logo">
			<img src="image/c3-logo.png" alt="ロゴ">
		</div>
		</header>
 
<c:if test = "${not empty lives}">

<div class="live-list">
<c:forEach var = "live" items="${lives}">

<form action="/c3/HomeAdminServlet"	method = "POST">
<button type="submit" name="liveId" value="${live.id}" class = "date">
        ${live.begin_date}
    </button>
    </form>
</c:forEach>
</div>

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