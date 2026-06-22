<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ライブ運営助けるくん</title>
<link rel = "stylesheet" href="${pageContext.request.contextPath}/css/common.css">
<link rel = "stylesheet" href="${pageContext.request.contextPath}/css/live_show.css">
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
						<form action="LoginServlet" method="post">
							<button type="submit">ログアウト</button>
						</form>
					</li>
					<li>
						<form action="ModBandServlet" method="get">
							<button type="submit">バンド情報</button>
						</form>
					</li>

					<li>
						<form action="PreparRegistServlet" method="get">
							<button type="submit">準備情報</button>
						</form>
					</li>
				</ul>
			</nav>

			<div class="black-bg" id="js-black-bg"></div>
		</div>
		<div class="logo">
			<img src="image/c3-logo.png" alt="ロゴ">
		</div>
<c:choose>

  <c:when test="${not empty lives}">
    <c:forEach var="live" items="${lives}">
      <form action="/c3/LiveShowServlet" method="POST">
        <button type="submit" name="liveId" value="${live.id}" class="date">
          ${live.begin_date}
        </button>
      </form>
    </c:forEach>
  </c:when>

  <c:otherwise>
    <div class="center-message">
      まだライブの予定がありません
    </div>
  </c:otherwise>

</c:choose>
<script>
const noTimeTable = ${noTimeTable != null && noTimeTable ? "true" : "false"};
</script>

<script src="javascript/common.js"></script>
<script src="javascript/live_show.js"></script>

</body>
</html>