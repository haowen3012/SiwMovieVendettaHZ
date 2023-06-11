package it.uniroma3.siw.hz.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.Collection;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;



    private String title;

    @Min(1)
    @Max(5)
    private int rating;


    @Column(length = 500)
    private String comment;

    @ManyToOne
    private User author;

   @ManyToOne
    private Movie reviewedMovie;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Movie getReviewedMovie() {
        return reviewedMovie;
    }

    public void setReviewedMovie(Movie reviewedMovie) {
        this.reviewedMovie = reviewedMovie;
    }
}
