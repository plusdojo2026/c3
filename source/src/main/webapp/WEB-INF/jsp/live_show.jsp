<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href="/webapp/css/live_show.css">
<link rel = "stylesheet" href="/webapp/css/common.css">
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
                <li><button type= "button" href="LoginServlet">ログアウト</button></li>
                <li><button type= "button" href="ModBandServlet">バンド情報</button></li>
                 <li><button type= "button" href="LiveShowServlet">ライブ情報</button></li>
          </ul>
            
        </nav>
        <div class="black-bg" id="js-black-bg"></div>
    </div>
    </header>

<c:if test = "${not empty lives}">
<c:forEach var = "live" items="${lives}">

<form action="/c3/LiveShowServlet"	method = "POST">
<input type = "submit" value = "${live.begin_date}" class = "date">
<input type= "hidden" value = "${live.id}" name = "test" >
</form>
</c:forEach>
</c:if>

<script>
const noLiveInfo = ${noLiveInfo ? "true" : "false"};
</script>

<script>
const noTimeTable = ${noTimeTable ? "true" : "false"}
</script>

<script src="javascript/common.js"></script>
<script src="javascript/home_admin.js"></script>

</body>
</html>