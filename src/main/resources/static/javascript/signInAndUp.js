

    var login = document.getElementById('login');
    var sign_in = document.getElementById('sign-in');

    var sign_up = document.getElementById('sign-up');

    var close = document.querySelector('.close');
    var close_2 = document.querySelector('.close-2');

    login.addEventListener('click',()=>{
    sign_in.style.display = " flex";
    sign_in.style.animation ="none";
    sign_up.style.animation ="none";
    login.style.pointerEvents="none";



});


    function register(){

    sign_in.style.animation = " shrink 0.5s ease-out";

    setTimeout( function(){
    sign_in.style.display= "none";}, 400);



    sign_up.style.display= "flex";
    sign_up.style.animation = " big_up 0.5s ease-out";

}

    function log_in(){

    sign_up.style.animation = " shrink 0.5s ease-out";

    setTimeout( function(){
    sign_up.style.display= "none";}, 400);



    sign_in.style.display= "flex";
    sign_in.style.animation = " big_up 0.5s ease-out";

}

    close.addEventListener('click',()=>{

    sign_in.style.display= "none";
    login.style.pointerEvents="auto";


});

    close_2.addEventListener('click',()=>{


    sign_up.style.display= "none";
    login.style.pointerEvents="auto";

});

