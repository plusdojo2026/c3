<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href="/webapp/css/live_show.css">
</head>
<body>

<nav class = "nav">
          <ul>
                <li><a href="LoginServlet">ログアウト</a></li>
                <li><a href="ModBandServlet">バンド情報</a></li>
                 <li><a href="LiveShowServlet">ライブ情報</a></li>
          </ul>
</nav>
<form method ="POST" action = "/webapp/LiveShowServlet"">
<input type = "submit" name = "button1" value = "開催日" class = "date blue">
<input type = "submit" name = "button2" value = "開催日" class = "date red">
<input type = "submit" name = "button3" value = "開催日" class = "date purple">
</form>

<script>
    'use Strict'

    const button1 = document.querySelectorAll('.date blue');
         
          buttons.forEach (button => {
            button.addEventListener ('click', function()  {
            window.alert ("まだライブの予定がありません。");
         });
         });
    const button1 = document.querySelectorAll('.date blue');
          
          buttons.forEach (button => {
            button.addEventListener ('click', function()  {
            window.alert ("まだタイムテーブルが作成されていません");
         });
         });

    const button2 = document.querySelectorAll('.date red');
         
          buttons.forEach (button => {
            button.addEventListener ('click', function()  {
            window.alert ("まだテーブルが作成されていません。");
         });
         });

    const button3 = document.querySelectorAll('.date purple');
         
          buttons.forEach (button => {
            button.addEventListener ('click', function()  {
            window.alert ("まだライブの予定がありません。");
         });
         });

</script>
</body>
</html>