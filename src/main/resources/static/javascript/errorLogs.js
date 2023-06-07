var error_element = document.getElementById('errors');

error_element.addEventListener('animationend', () => {

    setTimeout( function(){
        error_element.style.animation="slide-out-up 1s ease-out forwards";
    },2000);


    setTimeout( function(){
        error_element.innerHTML="";
    },2600);
});