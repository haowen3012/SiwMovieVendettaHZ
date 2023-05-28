package it.uniroma3.siw.hz.repository;

import it.uniroma3.siw.hz.model.Movie;
import it.uniroma3.siw.hz.model.Review;
import it.uniroma3.siw.hz.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface ReviewRepository extends CrudRepository<Review,Long> {


    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.reviewedMovie = :movie")
    Double findAverageRatingByMovie(@Param("movie") Movie movie);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.reviewedMovie = :movie")
    Integer countReviewsByMovie(@Param("movie") Movie movie);


    Collection<Review> findByAuthor(User author);



}
