

document.getElementById('plus').addEventListener('click', function() {
    document.getElementById('file').click();
});

var thumbnail = document.getElementById('thumbnail');

$(document).ready(function(){
    $('#file').change(function(){
        showThumbnail(this);
    });

});

function  showThumbnail(fileInput){


    file = fileInput.files[0];
    reader = new FileReader();

    reader.onload = function(e){
        $('#thumbnail').attr('src', e.target.result);
    };

    reader.readAsDataURL(file);

    thumbnail.style.border ="none";
}

