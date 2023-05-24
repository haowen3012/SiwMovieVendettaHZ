package it.uniroma3.siw.hz.repository;

import java.time.LocalDate;
import java.util.List;

import it.uniroma3.siw.hz.model.Movie;
import org.springframework.data.repository.CrudRepository;


public interface MovieRepository extends CrudRepository<Movie, Long> {

	/*public List<Movie> findByYear(int year);*/

	public boolean existsByTitleAndReleaseDate(String title, LocalDate releaseDate);
}