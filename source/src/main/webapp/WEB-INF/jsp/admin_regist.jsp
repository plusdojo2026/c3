<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>管理者、運営スタッフ登録</title>
<link rel="icon" type="image/png" href="image/liveicon.png">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/admin_regist.css">
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
						<form action="HomeAdminServlet" method="get">
							<button type="submit">ホーム</button>
						</form>
					</li>
					<li>
						<form action="AdminRegistServlet" method="get">
							<button type="submit">新規登録</button>
						</form>
					</li>
					<li>
						<form action="LoginServlet" method="post">
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
	<div class="container">
		<div class="right">
			<div class="form-container">
				<div class="tabs">
					<button class="tab active" onclick="showLogin()">管理者</button>
					<button class="tab" onclick="showRegister()">運営スタッフ</button>
				</div>

				<!-- ログイン -->
				<div id="loginForm">
					<h2>管理者登録</h2>
					<p id="loginError" class="error"></p>
					<form action="/c3/AdminRegistServlet" method="post"
						onsubmit="return login()">
						<input type="hidden" name="mode" value="login"> <label><span
							class="required">ID</span></label> <input type="text" id="loginId"
							name="id"> <label><span class="required">パスワード</span></label>
						<input type="password" id="loginPassword" name="pw">

						<button type="submit" class="submit-btn">登録</button>
					</form>
				</div>

				<!-- 新規登録 -->
				<div id="registerForm" style="display: none;">
					<h2>運営スタッフ登録</h2>
					<p id="registError" class="error"></p>
					<form action="/c3/AdminRegistServlet" method="post"
						onsubmit="return regist()">
						<input type="hidden" name="mode" value="register"> <label><span
							class="required">ID</span></label> <input type="text" id="registId"
							name="id"> <label><span class="required">パスワード</span></label>
						<input type="password" id="registPassword" name="pw">

						<button type="submit" class="submit-btn">登録</button>
					</form>
				</div>
			</div>
		</div>

	</div>

	<script src="javascript/common.js"></script>
	<script src="javascript/admin_regist.js"></script>

</body>

</html>