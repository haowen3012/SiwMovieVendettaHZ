

    function validation(stringa){
    var form = document.getElementById('form');
    var variable = document.getElementById(stringa);
    var variable_valid = document.getElementById(stringa + '-valid');

    var check = document.getElementById('check-'+ stringa);
    var times = document.getElementById('times-' + stringa);

    var container = document.getElementById('id-' + stringa);




    variable_valid.style.fontSize ="9px";
    variable_valid.style.fontStyle ="italic";
    variable_valid.style.fontWeight ="300";


    var pattern;

    switch(stringa){

    case 'username': pattern = /.{4,20}/; break;
    case 'password': pattern = /.{6,20}/; break;
    case 'firstname': pattern = /.{1,100}/; break;
    case 'lastname': pattern = /.{1,100}/; break;

}


    if(variable.value.match(pattern)){
    form.classList.add("valid");
    form.classList.remove("invalid");

    check.style.display = "block";
    times.style.display ="none";
    container.style.boxShadow="0 1px 7px 0 rgba(124, 252, 0)";

    switch(stringa){
    case 'username':variable_valid.innerHTML = "Your username is valid!"; break;
    case 'password': variable_valid.innerHTML = "Your password is valid!";break;
    case 'firstname':variable_valid.innerHTML = "Your firstname is valid!"; break;
    case 'lastname':variable_valid.innerHTML = "Your lastname is valid!";break;
}
    variable_valid.style.color= "#00ff00";



}else{
    form.classList.add("invalid");
    form.classList.remove("valid");

    check.style.display = "none";
    times.style.display ="block";

    container.style.boxShadow="0 1px 7px 0 red";


    switch(stringa){
    case 'username':variable_valid.innerHTML = "Your username must be between 4 and 20 characters long!"; break;

    case 'password': variable_valid.innerHTML = "Your password must be between 6 and 20 characters long!"; break;
    case 'firstname':variable_valid.innerHTML = "Your firstname must be between 2 and 100 characters long!"; break;
    case 'lastname':variable_valid.innerHTML = "Your lastname must be between 2 and 100 characters long!"; break;
}
    variable_valid.style.color = "#ff0000";

    setTimeout( function(){
    variable_valid.innerHTML = "";},3000);


}


}