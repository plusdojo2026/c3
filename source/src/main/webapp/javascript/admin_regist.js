function showLogin(){

    document.getElementById("loginForm").style.display = "block";
    document.getElementById("registerForm").style.display = "none";

    document.getElementsByClassName("tab")[0].classList.add("active");
    document.getElementsByClassName("tab")[1].classList.remove("active");
}

function showRegister(){

    document.getElementById("loginForm").style.display = "none";
    document.getElementById("registerForm").style.display = "block";

    document.getElementsByClassName("tab")[1].classList.add("active");
    document.getElementsByClassName("tab")[0].classList.remove("active");
}
function login(){

    const text = document.getElementById("loginId").value;
    const password = document.getElementById("loginPassword").value;

    const error = document.getElementById("loginError");

    // 一旦エラーメッセージを消す
    error.textContent = "";

    if(text === "" && password === "" ){
        error.textContent = "IDとパスワードを入力してください";
        return false;
    }
    if(text === "" ){
        error.textContent = "IDを入力してください";
        return false;
    }
    if(password === "" ){
        error.textContent = "パスワードを入力してください";
        return false;
    }

    return true;
}
function regist(){

    const text = document.getElementById("registId").value;
    const password = document.getElementById("registPassword").value;

    const error = document.getElementById("registError");

    // 一旦エラーメッセージを消す
    error.textContent = "";

    if(text === "" && password === ""){
        error.textContent = "IDとパスワードを入力してください";
        return false;
    }
    if(text === "" ){
        error.textContent = "IDを入力してください";
        return false;
    }
    if(password === "" ){
        error.textContent = "パスワードを入力してください";
        return false;
    }
return true;

}
