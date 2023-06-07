package it.uniroma3.siw.hz;

import it.uniroma3.siw.hz.controller.validator.MultipartFileValidator;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public class FileUploadWrapper {


    private MultipartFile image;


    private Collection<MultipartFile> movieScenes;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Collection<MultipartFile> getMovieScenes() {
        return movieScenes;
    }

    public void setMovieScenes(Collection<MultipartFile> movieScenes) {
        this.movieScenes = movieScenes;
    }
}
