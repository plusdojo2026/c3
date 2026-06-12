    'use Strict'

     const blueButton = document.querySelector('.date.blue');
    const redButton = document.querySelector('.date.red');
    const purpleButton = document.querySelector('.date.purple');

    blueButton.addEventListener('click', function(event){
        event.preventDefault();
        alert('まだライブ情報が登録されていません。')
    });

     redButton.addEventListener('click', function(event){
        event.preventDefault();
        ('まだライブ情報が登録されていません。')
    });

     purpleButton.addEventListener('click', function(event){
        event.preventDefault();
        ('まだライブ情報が登録されていません。')
     });

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