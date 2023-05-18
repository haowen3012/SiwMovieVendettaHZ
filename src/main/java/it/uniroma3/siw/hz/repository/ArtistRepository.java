package it.uniroma3.siw.hz.repository;


import it.uniroma3.siw.hz.model.Artist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;



public interface ArtistRepository extends CrudRepository<Artist, Long> {

	public boolean existsByNameAndSurname(String name, String surname);	

	@Query(value="select * "
			+ "from artist a "
			+ "where a.id not in "
			+ "(select actors_id "
			+ "from movie_actors "
			+ "where movie_actors.starred_movies_id = :movieId)", nativeQuery=true)
	public Iterable<Artist> findActorsNotInMovie(@Param("movieId") Long id);




}