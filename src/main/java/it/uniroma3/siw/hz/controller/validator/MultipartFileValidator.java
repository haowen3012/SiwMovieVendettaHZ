package it.uniroma3.siw.hz.controller.validator;

import it.uniroma3.siw.hz.FileUploadWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MultipartFileValidator implements Validator {





    @Override
    public boolean supports(Class<?> clazz) {
        return FileUploadWrapper.class.equals(clazz);
    } // specifica la classe su cui opera il validator

    @Override
    public void validate(Object o, Errors errors) {

        FileUploadWrapper fileUploadWrapper = (FileUploadWrapper) o;


        if( fileUploadWrapper.getImage()!=null && !fileUploadWrapper.getImage().isEmpty() &&  !fileUploadWrapper.getImage().getOriginalFilename().endsWith(".png") &&
                !fileUploadWrapper.getImage().getOriginalFilename().endsWith(".jpg")){

            errors.rejectValue("movieImage","invalidFormat.fileUploadWrapper.movieImage");

        }


        if(fileUploadWrapper.getMovieScenes()!=null ) {

            for(MultipartFile scene : fileUploadWrapper.getMovieScenes()) {

                if (scene!= null && !scene.isEmpty() && !scene.getOriginalFilename().endsWith(".png") &&
                        !scene.getOriginalFilename().endsWith(".jpg")) {

                    errors.rejectValue("movieScenes", "invalidFormat.fileUploadWrapper.movieScenes");

                }
            }

        }




    }
}
