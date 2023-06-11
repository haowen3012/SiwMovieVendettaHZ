

    var event_war = document.getElementById('event-warn');

    deleted_war.addEventListener('animationend', () => {

        setTimeout(function () {
            event_war.style.animation = "slide-out-up 1s ease-out forwards";
        }, 2000);


        setTimeout(function () {
            event_war.innerHTML = "";
        }, 2600);
    });


