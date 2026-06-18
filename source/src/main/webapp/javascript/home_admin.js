    'use strict'

     window.onload = function () {
	
	if (noEntranceMusic === true || noEntranceMusic === "true") {
		alert("出演者からの準備情報が不足しています");
	}
	
	
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