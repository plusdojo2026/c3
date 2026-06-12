<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ログイン画面</title>
    <link rel="stylesheet" href="CSS/login.css"> </head> <body>

<div class="container">

    <!-- 左側 -->
    <div class="left">
        
        <img src="image/Black-splash-2506_1-11-37.png" alt="画像">
    </div>

    <!-- 右側 -->
    <div class="right">
        
        <div class="tabs">
            <button class="tab active" onclick="showLogin()">ログイン</button>
            <button class="tab" onclick="showRegister()">新規登録</button>
        </div>
        
        <!-- ログイン -->
        <div id="loginForm">
            <h2>ログイン</h2>
            <p id="loginError" class="error"></p>
            <label><span class="required">ID</span></label>
            <input type="text" id="loginId">

            <label><span class="required">パスワード</span></label>
            <input type="password" id="loginPassword">

            <button class="submit-btn" onclick="login()">ログイン</button>
        </div>

        <!-- 新規登録 -->
        <div id="registerForm" style="display:none;">
            <h2>新規登録</h2>
            <p id="registError" class="error"></p>
            <label><span class="required">ID</span></label>
            <input type="text" id="registId">

            <label><span class="required">パスワード</span></label>
            <input type="password" id="registPassword">

            <button class="submit-btn" onclick="regist()">登録</button>
        </div>

    </div>

</div>

<script src="login.js"></script>

</body>
</html>
    