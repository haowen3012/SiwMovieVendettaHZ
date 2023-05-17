package it.uniroma3.siw.hz.repository;

import java.util.List;

import it.uniroma3.siw.hz.model.Movie;
import org.springframework.data.repository.CrudRepository;


public interface MovieRepository extends CrudRepository<Movie, Long> {

	public List<Movie> findByYear(int year);

	public boolean existsByTitleAndYear(String title, int year);	
}