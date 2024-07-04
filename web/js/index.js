var firstNameCheck;
var lastNameCheck;

function validatePassword() {
    var password = document.getElementById("password");
    var repeatPassword = document.getElementById("repeat-password");
    var passwordError = document.getElementById("password-error");
    var loginButton = document.getElementById("login-button");

    if (password.value !== repeatPassword.value) {
        passwordError.classList.remove("hidden");
        loginButton.disabled = true;
    } else {
        passwordError.classList.add("hidden");
        loginButton.disabled = false;
    }
}



function validatePasswordCharacter() {
    var password = document.getElementById("password");
    var passwordError = document.getElementById("password-error-input");
    var regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;

    if (!regex.test(password.value)) {
        passwordError.classList.remove("hidden");
    } else {
        passwordError.classList.add("hidden");
    }
}
function validateFirstName(){
    var firstname = document.getElementById("first-name");
    var firstnameErr = document.getElementById("first-name-error");
    var regex = /^[A-Za-z]{1,30}$/;
    if(!regex.test(firstname.value)){
        firstnameErr.classList.remove("hidden");
    }else{
        firstnameErr.classList.add("hidden");

    }
    updateCheck()
}
function validateLastName(){
    var lastname = document.getElementById("last-name");
    var lastnameErr = document.getElementById("last-name-error");
    var regex = /^[A-Za-z]{1,30}$/;
    if(!regex.test(lastname.value)){
        lastnameErr.classList.remove("hidden");

    }else{
        lastnameErr.classList.add("hidden");

    }
    updateCheck();
}
function updateCheck(){
   var updateButton = document.getElementById("update-button");
   var firstnameErr = document.getElementById("first-name-error");
   var lastnameErr = document.getElementById("last-name-error");
   if( firstnameErr.classList.contains("hidden") && lastnameErr.classList.contains("hidden")){
       updateButton.disabled = false;
   }
   else{
       updateButton.disabled = true;
   }
}
function validatePassword1() {
    var password = document.getElementById("newpassword");
    var repeatPassword = document.getElementById("repeat-password");
    var passwordError = document.getElementById("password-error");
    var loginButton = document.getElementById("login-button");

    if (password.value !== repeatPassword.value) {
        passwordError.classList.remove("hidden");
        loginButton.disabled = true;
    } else {
        passwordError.classList.add("hidden");
        loginButton.disabled = false;
    }
}
function validatePasswordCharacter1() {
    var password = document.getElementById("newpassword");
    var passwordError = document.getElementById("password-error-input");
    var regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;

    if (!regex.test(password.value)) {
        passwordError.classList.remove("hidden");
    } else {
        passwordError.classList.add("hidden");
    }
}
