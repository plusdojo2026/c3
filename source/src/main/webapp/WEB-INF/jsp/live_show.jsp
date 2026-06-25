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
						<form action="HomeBandServlet"  method="get">
							<button type="submit">ホーム</button>
						</form>
					</li>
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

				</ul>
			</nav>

			<div class="black-bg" id="js-black-bg"></div>
		</div>
		<div class="logo">
			<img src="image/c3-logo.png" alt="ロゴ">
		</div>
		</header>
<c:choose>

  <c:when test="${not empty lives}">
  <div class="live-list">
  
  <h2 class="live-title">ライブ一覧</h2>
  <div class="status-legend">
	<div class="legend-item">
	<span class ="legend-color test2"></span>
	準備情報未登録
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
	
    <c:forEach var="live" items="${lives}">
      <form action="/c3/LiveShowServlet" method="POST">
        <button type="submit" 
        		name="liveId" 
        		value="${live.id}" 
        		class = "date ${statusMap[live.id]}"
        		title="${live.name}">
          ${fn:replace(fn:substring(live.begin_date,0,10),"-","/")}
          <br>
          ${fn:substring(live.begin_date,11,16)}~
        </button>
      </form>
    </c:forEach>
    </div>
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