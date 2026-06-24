<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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

<h2 class="live-title">ライブ日程一覧</h2>
<div class="status-legend">
	<div class="legend-item">
	<span class ="legend-color test2"></span>
	準備情報不足
	</div>
	<div class="legend-item">
	<span class="legend-color test3"></span>
	タイムテーブル未作成
	</div>
	<div class="legend-item">
	<span class="legend-color test4"></span>
	タイムテーブル作成済み
	</div>
</div>
<c:forEach var = "live" items="${lives}">

<form action="/c3/HomeAdminServlet"	method = "POST">
<button type="submit" 
		name="liveId" 
		value="${live.id}" 
		class = "date ${statusMap[live.id]}"
		title="${live.name}">
        ${fn: replace(fn:substring(live.begin_date, 0, 10), "-", "/")}
<br>
${fn:substring(live.begin_date, 11,16)}~
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