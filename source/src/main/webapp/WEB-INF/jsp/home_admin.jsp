<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 出演者が情報を登録していない時 
「まだライブ情報が登録されていません」とアラートを表示する-->
<!-- 出演者が情報を登録したが、管理者がタイムテーブルを作成していない時 
TableCreateServlrtにつなぐ-->
<!-- すでにタイムテーブルが作成されているもの
 TableShowServletにつなぐ-->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ライブ運営助けるくん</title>
<link rel = "stylesheet" href="/webapp/css/home_admin.css">
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
             <li><a href="MenuServlet">ホーム</a></li>
                <li><a href="AdminRegistServlet" >新規登録</a></li>
                <li><a href="LogingServlet" >ログアウト</a></li>
                <li><a href="LiveCreateServlet" >ライブ情報作成</a></li>
            </ul>
        </nav>
        <div class="black-bg" id="js-black-bg"></div>
        </div>
    </div>
    </header>

<form action = "/webapp/HomeAdminSrvlet">
<input name = "button1" type = "submit" value = "開催日" class = "date blue" >
<input name = "button2" type = "submit" value = "開催日" class = "date red">
<input name = "button3" type = "submit" value = "開催日" class = "date purple">
</form>

</body>

</html>