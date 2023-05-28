package it.uniroma3.siw.hz.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import it.uniroma3.siw.hz.model.Movie;
import it.uniroma3.siw.hz.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface MovieRepository extends CrudRepository<Movie, Long> {

	/*public List<Movie> findByYear(int year);*/

	public boolean existsByTitleAndReleaseDate(String title, LocalDate releaseDate);

	@Query("SELECT m FROM Movie m WHERE  EXISTS (SELECT r FROM Review r WHERE r.reviewedMovie = m AND r.author = :user)")
	List<Movie> findMoviesNotReviewedByUser(@Param("user") User user);
}