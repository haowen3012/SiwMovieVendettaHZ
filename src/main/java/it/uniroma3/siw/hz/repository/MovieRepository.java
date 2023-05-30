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


	@Query("SELECT m FROM Movie m WHERE m.releaseDate >= :startDate")
	List<Movie> findMoviesReleasedInLast30Days(@Param("startDate") LocalDate startDate);


	@Query("SELECT m, COUNT(r) AS reviewCount,  COALESCE(AVG(r.rating), 0) AS avgRating " +
			"FROM Movie m\n" +
			"LEFT JOIN Review r ON r.reviewedMovie.id = m.id\n" +
			" GROUP BY m.id ORDER BY AVG(r.rating) ")
	List<Object[]> findMoviesOrderByAverageRatingWithCountAndAvgRating();



		@Query("SELECT m, COUNT(r) AS reviewCount, COALESCE(AVG(r.rating), 0) AS avgRating\n" +
				"FROM Movie m\n" +
				"LEFT JOIN Review r ON r.reviewedMovie.id = m.id\n" +
				"GROUP BY m.id\n" +
				"ORDER BY reviewCount DESC")
		List<Object[]> findMoviesOrderByMostReviewsWithCountAndAvgRating();


}