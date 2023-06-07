package it.uniroma3.siw.hz.controller.validator;

import it.uniroma3.siw.hz.model.Artist;
import it.uniroma3.siw.hz.model.Movie;
import it.uniroma3.siw.hz.repository.ArtistRepository;
import it.uniroma3.siw.hz.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class ArtistValidator implements Validator {
    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public void validate(Object o, Errors errors) {
        Artist artist = (Artist) o;
        System.out.println(errors);
        if (artist.getName() !=null &&  artist.getSurname()!=null && artist.getDateOfBirth() != null
                && artistRepository.existsByNameAndSurnameAndDateOfBirth(artist.getName(), artist.getSurname(),artist.getDateOfBirth())) {
            errors.reject("artist.duplicate");

        }
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return Artist.class.equals(aClass);
    }
}