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
                <li><a href="LoginServlet">ログアウト</a></li>
                <li><a href="ModBandServlet">バンド情報</a></li>
                 <li><a href="LiveShowServlet">ライブ情報</a></li>
          </ul>
            
        </nav>
        <div class="black-bg" id="js-black-bg"></div>
    </div>
    </header>

<form action = "LiveShowServlet">
<input name = "button1" type = "submit" value = "開催日" class = "date blue" >
<input name = "button2" type = "submit" value = "開催日" class = "date red">
<input name = "button3" type = "submit" value = "開催日" class = "date purple">
</form>

</body>
</html>