'use Strict'
    
    window.onload = function () {
	
	if (noLiveInfo === true || noLiveInfo === "true") {
		alert("まだライブの予定がありません");
	}
	
	if (noTimeTable === true || noTimeTable === "true") {
		alert("まだタイムテーブルが作成されていません");
	}

    window.onload = function () {
    const nav = document.getElementById('nav-wrapper');
    const hamburger = document.getElementById('js-hamburger');
    const blackBg = document.getElementById('js-black-bg');
    const spNav = document.querySelector('.sp-nav');

    hamburger.addEventListener('click', function (e) {
        e.stopPropagation();
        nav.classList.toggle('open');
    });

    blackBg.addEventListener('click', function () {
        nav.classList.remove('open');
    });

    spNav.addEventListener('click', function (e) {
        e.stopPropagation();
    });
};/**
 * 
 */