<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ライブ運営助けるくん</title>
<link rel="icon" type="image/png" href="image/liveicon.png">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/live_create.css">
</head>
<body>
	<!-- タブ表示 -->
	<!-- ロゴ表示 -->
	<header class="header">
		<div id="nav-wrapper" class="nav-wrapper">
			<div class="hamburger" id="js-hamburger">
				<span class="hamburger__line hamburger__line--1"></span> <span
					class="hamburger__line hamburger__line--2"></span> <span
					class="hamburger__line hamburger__line--3"></span>
			</div>
			<nav class="sp-nav">
				<ul>
					<li><form action="HomeAdminServlet" method="GET">
                    <button type="submit">ホーム</button>
                    </form></li>
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

	<!-- メイン -->
	<main>
		<!-- フォーム群 -->

		<form action="/c3/LiveCreateServlet" method="POST" id="live_create">
			<div class="live_name">
				<!-- ライブ名入力 -->
				ライブ名<span class="required"></span><br> <input type="text"
					placeholder="ライブ名" name="live_name">
			</div>
			<div class="set_date">
				<!-- 開始日時入力 -->
				<br>開始日時<span class="required"></span><br> <input
					type="datetime-local" placeholder="開始日時" name="begin_date" min="" id="begin_date">
				<!-- 終了日時入力 -->
				<br>終了日時<span class="required"></span><br> <input
					type="datetime-local" placeholder="終了日時" name="end_date" min="" id="end_date">
			</div>
			<!-- エラーメッセージ表示 -->
			<!-- 出演者(バンド名) & 各バンドの持ち時間 -->
			<div class="band_info">
				<input type="hidden" name="band_num" value="1" />
				<table id="bands_info_row">
					<tr>
						<th>バンド名<span
							class="required"></span></th>
						<th>持ち時間<span class="required"></span></th>
						<td><button type="button" onclick="addRow()">
								<img src="image/plus.svg" alt="">
							</button></td>
					</tr>
					
					<!-- バンド情報がある場合の処理 -->
					<c:if test="${not empty band_infos }">
						<tr class="band_info_row">
							<td>
							<div class="search_select_container">
									<input type="hidden" name="band_infos[0]" class="real_submit_value">
									<input type="text" class="search_input" placeholder="バンド名" >
									<!-- ドロップダウンリスト形式のリストを作成 -->
									<ul class="options_list">
										<c:forEach var="band_info" items="${band_infos }">
											<li data-value="${band_info.id }" ><c:out value="${band_info.name }" /></li>
										</c:forEach>
									</ul>
							</div></td>
							<td><input type="number" name="time[0]" placeholder="持ち時間" step="5" min="0"></td>
							<td>
								<button type="button" onclick="addRow()">
									<img src="image/plus.svg" alt="">
								</button>
								<button type="button" onclick="removeRow(this)">
									<img src="image/delete.svg" alt="">
								</button>
							</td>
						</tr>
						<tr class="band_info_row">
							<td>
							<div class="search_select_container">
									<input type="hidden" name="band_infos[1]" class="real_submit_value">
									<input type="text" class="search_input" placeholder="バンド名" >
									<!-- ドロップダウンリスト形式のリストを作成 -->
									<ul class="options_list">
										<c:forEach var="band_info" items="${band_infos }">
											<li data-value="${band_info.id }"><c:out value="${band_info.name }" /></li>
										</c:forEach>
									</ul>
							</div></td>
							<td><input type="number" name="time[1]" placeholder="持ち時間" step="5" min="0"></td>
							<td>
								<button type="button" onclick="addRow()">
									<img src="image/plus.svg" alt="">
								</button>
								<button type="button" onclick="removeRow(this)">
									<img src="image/delete.svg" alt="">
								</button>
							</td>
						</tr>
					</c:if>

					<!-- バンド情報が場合ないの処理 -->
					<c:if test="${empty band_infos }">
						<tr class="band_info_row">
							<td>
							<div class="search_select_container">
									<input type="hidden" name="band_infos[0]" class="real_submit_value">
									<input type="text" class="search_input" placeholder="バンド名" name="band_name_text">
									<!-- ドロップダウンリスト形式のリストを作成 -->
									<ul class="options_list">
										<li data-value="0">バンド情報がありません</li>
									</ul>
							</div></td>
							<td><input type="number" name="time[0]" placeholder="持ち時間" step="5" min="0"></td>
							<td>
								<button type="button" onclick="addRow()">
									<img src="image/plus.svg" alt="">
								</button>
								<button type="button" onclick="removeRow(this)">
									<img src="image/delete.svg" alt="">
								</button>
							</td>
						</tr>
					</c:if>

					<!-- バンド情報がある場合のテンプレート -->
					<c:if test="${not empty band_infos }">
					<template id="band_row_template">
						<tr class="band_info_row">
							<td>
							<div class="search_select_container">
									<input type="hidden" name="band_infos_temp" class="real_submit_value">
									<input type="text" class="search_input" placeholder="バンド名" >
									<!-- ドロップダウンリスト形式のリストを作成 -->
									<ul class="options_list">
										<c:forEach var="band_info" items="${band_infos }">
											<li data-value="${band_info.id }"><c:out value="${band_info.name }" /></li>
										</c:forEach>
									</ul>
							</div></td>
							<td><input type="number" name="time_temp" placeholder="持ち時間" step="5" min="0"></td>
							<td>
								<button type="button" onclick="addRow()">
									<img src="image/plus.svg" alt="">
								</button>
								<button type="button" onclick="removeRow(this)">
									<img src="image/delete.svg" alt="">
								</button>
							</td>
						</tr>
						</template>
					</c:if>

					<!-- バンド情報がない場合のテンプレート -->
					<c:if test="${empty band_infos }">
					<template id="band_row_template">
						<tr class="band_info_row">
							<td>
							<div class="search_select_container">
									<input type="hidden" name="band_infos_temp" id="select_box" class="real_submit_value">
									<input type="text" class="search_input" placeholder="バンド名" >
									<!-- ドロップダウンリスト形式のリストを作成 -->
									<ul class="options_list">
										<li data-value="0">バンド情報がありません</li>
									</ul>
							</div></td>
							<td><input type="number" name="time_temp" placeholder="持ち時間" step="5" min="0"></td>
							<td>
								<button type="button" onclick="addRow()">
									<img src="image/plus.svg" alt="">
								</button>
								<button type="button" onclick="removeRow(this)">
									<img src="image/delete.svg" alt="">
								</button>
							</td>
						</tr>
						</template>
					</c:if>

				</table>
			</div>
			<p id="blank_alert" class="error"></p>
			<div class="cancel_create_btn">
				<!-- キャンセルボタン -->
				<a href="HomeAdminServlet" class="cancel_btn">キャンセル</a>
				<!-- 送信 -->
				<input type="submit" name="create" id="create" value="送信">
			</div>
		</form>
	</main>

	<script src="javascript/common.js"></script>
	<script src="javascript/live_create.js"></script>
</body>
</html>