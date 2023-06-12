package it.uniroma3.siw.hz.controller.validator;

import it.uniroma3.siw.hz.model.Movie;
import it.uniroma3.siw.hz.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;


@Component
public class MovieValidator implements Validator {

	private final LocalDate MIN_RELEASE_DATE = LocalDate.of(1900,1,1);
	private final LocalDate MAX_RELEASE_DATE = LocalDate.now();
	@Autowired
	private MovieRepository movieRepository;

	private boolean updating = false;


	public  void setUpdating(boolean updating){
		this.updating = updating;
	}


	@Override
	public void validate(Object o, Errors errors) {
		Movie movie = (Movie)o;
		System.out.println(errors);
		if (!updating && movie.getTitle() !=null &&  movie.getReleaseDate() != null
				&& movieRepository.existsByTitleAndReleaseDate(movie.getTitle(), movie.getReleaseDate())) {
			errors.reject("movie.duplicate");

		}

		if(movie.getReleaseDate().isBefore(MIN_RELEASE_DATE)){

			errors.rejectValue("releaseDate","minInvalidDate.movie.releaseDate");
		}

		if(movie.getReleaseDate().isAfter(MAX_RELEASE_DATE)){


			errors.rejectValue("releaseDate","maxInvalidDate.movie.releaseDate");
		}




	}
	@Override
	public boolean supports(Class<?> aClass) {
		return Movie.class.equals(aClass);
	}
}