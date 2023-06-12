package it.uniroma3.siw.hz.controller.validator;

import it.uniroma3.siw.hz.model.Artist;
import it.uniroma3.siw.hz.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class ArtistValidator implements Validator {


    private final  Integer MIN_YEAR = 1940;
    private final  Integer MAX_YEAR = 2000;
    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public void validate(Object o, Errors errors) {
        Artist artist = (Artist) o;
        System.out.println(errors);
        if (artist.getName() !=null &&  artist.getSurname()!=null && artist.getDateOfBirth() != null
                && artistRepository.existsByNameAndSurnameAndDateOfBirth(artist.getName(), artist.getSurname(),artist.getDateOfBirth())) {
            errors.rejectValue("artist","artist.duplicate");

        }

        if(artist.getDateOfBirth()!=null && artist.getDateOfBirth().getYear()< MIN_YEAR){
            errors.rejectValue("dateOfBirth","minInvalidDate.artist.dateOfBirth");
        }

        if(artist.getDateOfBirth()!=null &&  artist.getDateOfBirth().getYear()> MAX_YEAR){
            errors.rejectValue("dateOfBirth","maxInvalidDate.artist.dateOfBirth");
        }

        if(artist.getDateOfDeath()!=null && artist.getDateOfBirth().isAfter(artist.getDateOfDeath())){

            errors.rejectValue("artist","artist.invalidBirthAndDeath");
        }
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return Artist.class.equals(aClass);
    }
}