package it.uniroma3.siw.hz.service;

import it.uniroma3.siw.hz.model.Movie;

import it.uniroma3.siw.hz.model.Review;
import it.uniroma3.siw.hz.model.User;
import it.uniroma3.siw.hz.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Collection;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;



    @Transactional
    public Double getAvarageRatingByMovie(Movie movie){

        return this.reviewRepository.findAverageRatingByMovie(movie);
    }


    @Transactional
    public Integer countReviewsByMovie(Movie movie){

        return this.reviewRepository.countReviewsByMovie(movie);
    }



    @Transactional
    public Collection<Review> getUserReviews(User user){

        return  this.reviewRepository.findByAuthor(user);
    }

    @Transactional
    public void deleteReview(Long idReview){
         this.reviewRepository.deleteById(idReview);
    }
}
