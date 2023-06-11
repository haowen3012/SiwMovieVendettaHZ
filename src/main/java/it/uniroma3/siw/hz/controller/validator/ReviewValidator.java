package it.uniroma3.siw.hz.controller.validator;

import it.uniroma3.siw.hz.model.Artist;
import it.uniroma3.siw.hz.model.Movie;
import it.uniroma3.siw.hz.model.Review;
import it.uniroma3.siw.hz.repository.ArtistRepository;
import it.uniroma3.siw.hz.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class ReviewValidator implements Validator {



    @Override
    public boolean supports(Class<?> clazz) {
        return Review.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}