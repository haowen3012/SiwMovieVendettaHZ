package it.uniroma3.siw.hz.controller.validator;

import it.uniroma3.siw.hz.model.Movie;
import it.uniroma3.siw.hz.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class MovieValidator implements Validator {
	@Autowired
	private MovieRepository movieRepository;

	@Override
	public void validate(Object o, Errors errors) {
		Movie movie = (Movie)o;
		if (movie.getTitle() !=null &&  movie.getReleaseDate() != null
				&& movieRepository.existsByTitleAndReleaseDate(movie.getTitle(), movie.getReleaseDate())) {
			errors.reject("movie.duplicate");
		}
	}
	@Override
	public boolean supports(Class<?> aClass) {
		return Movie.class.equals(aClass);
	}
}