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
</head>
<body>

<nav class = "nav">
          <ul>
                <li><a href="AdminRegistServlet">新規登録</a></li>
                <li><a href="LoginServlet">ログアウト</a></li>
                <li><a href="LiveCreateServlet">ライブ情報作成</a></li>
          </ul>
</nav>

<form method ="POST" action = "/webapp/HomeAdminServlet">
<input name = "button1" type = "submit" value = "開催日" class = "date blue">
<input name = "button2" type = "submit" value = "開催日" class = "date red">
<input name = "button3" type = "submit" value = "開催日" class = "date purple">
</form>

<script>
    'use Strict'

    const button1 = document.querySelectorAll('.date blue');
         
          buttons.forEach (button => {
            button.addEventListener ('click', function()  {
            window.alert ("まだライブ情報が登録されていません。");
         });
         });

         const button2 = document.querySelectorAll('.date red');
         
          buttons.forEach (button => {
            button.addEventListener ('click', function()  {
            window.alert ("まだライブ情報が登録されていません。");
         });
         });

         const button3 = document.querySelectorAll('.date purple');
         
          buttons.forEach (button => {
            button.addEventListener ('click', function()  {
            window.alert ("まだライブ情報が登録されていません。");
         });
         });
         
</script>
</body>
</html>