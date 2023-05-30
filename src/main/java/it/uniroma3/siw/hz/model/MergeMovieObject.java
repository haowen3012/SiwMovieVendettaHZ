package it.uniroma3.siw.hz.model;

public class MergeMovieObject {

    private Movie movie;

    private Long reviewCount;

    private Double avgRating;

    public MergeMovieObject(Movie movie, Long reviewCount, Double avgRating) {
        this.movie = movie;
        this.reviewCount = reviewCount;
        this.avgRating = avgRating;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Long getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Long reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }
}
