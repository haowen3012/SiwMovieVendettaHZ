package it.uniroma3.siw.hz.repository;


import it.uniroma3.siw.hz.model.Artist;
import it.uniroma3.siw.hz.model.Movie;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;


public interface ArtistRepository extends CrudRepository<Artist, Long> {

	public boolean existsByNameAndSurname(String name, String surname);	

	@Query(value="select * "
			+ "from artist a "
			+ "where a.id not in "
			+ "(select actors_id "
			+ "from movie_actors "
			+ "where movie_actors.starred_movies_id = :movieId)", nativeQuery=true)
	public Iterable<Artist> findActorsNotInMovie(@Param("movieId") Long id);


	Collection<Artist> findArtistByDirectedMoviesNotContaining(Movie movie);


	boolean existsByNameAndSurnameAndDateOfBirth(String name, String surname, LocalDate dateOfBirth);



	@Modifying
	@Query("UPDATE Movie m SET m.actors = NULL WHERE :artist MEMBER OF m.actors")
	void removeArtistFromMovies(@Param("artist") Artist artist);

	@Modifying
	@Query("UPDATE Movie m SET m.director = NULL WHERE m.director = :artist")
	void removeArtistFromDirectedMovies(@Param("artist") Artist artist);




}

