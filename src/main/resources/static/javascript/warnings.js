

var deleted_war = document.getElementById('event-warn');

deleted_war.addEventListener('animationend', () => {

    setTimeout( function(){
        error_element.style.animation="slide-out-up 1s ease-out forwards";
    },2000);


    setTimeout( function(){
        error_element.innerHTML="";
    },2600);
});
