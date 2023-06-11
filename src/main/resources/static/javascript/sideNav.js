

    var side_nav = document.getElementById('side_nav');

    function openSideNav(role){


    var user = document.getElementById('image-'+ role);

    side_nav.style.width="14%";
    side_nav.style.height="100%";
    side_nav.style.top="0";
    user.style.visibility = "hidden";

}

    function closeSideNav(role){

        var user = document.getElementById('image-'+ role);

    side_nav.style.width="0";
    side_nav.style.height="0";
    side_nav.style.top="-10%";
    user.style.visibility = "visible";


}
