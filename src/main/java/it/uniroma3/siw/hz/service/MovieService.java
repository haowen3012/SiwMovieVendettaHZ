package it.uniroma3.siw.hz.service;

import com.nimbusds.oauth2.sdk.id.Actor;
import it.uniroma3.siw.hz.FileUploadUtil;
import it.uniroma3.siw.hz.controller.session.SessionData;
import it.uniroma3.siw.hz.model.*;
import it.uniroma3.siw.hz.repository.ImageRepository;
import it.uniroma3.siw.hz.repository.MovieRepository;
import it.uniroma3.siw.hz.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.hibernate.internal.util.MutableLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class MovieService {


    @Autowired
    private MovieRepository movieRepository;


    @Autowired
    private ArtistService artistService;


    @Autowired
    private ImageRepository imageRepository;


    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private SessionData sessionData;


    @Transactional
    public Movie getMovie(Long id){
        return this.movieRepository.findById(id).get();
    }


    @Transactional
    public Collection<Movie> getAllMovies(){
        return (Collection<Movie>) this.movieRepository.findAll();

    }


/*    @Transactional
    public Collection<Movie> getMoviesByYear(int year){

        return this.movieRepository.findByYear(year);
    }*/


    @Transactional
    public Movie setDirectorToMovie(Long directorId, Long movieId){

        Artist director = this.artistService.getArtist(directorId);
        Movie movie = this.getMovie(movieId);
        movie.setDirector(director);
        return  this.movieRepository.save(movie);
    }


    @Transactional
    public  Collection<Artist> addActorToMovie(Movie movie, Artist actor){


        Set<Artist> actors = movie.getActors();
        actors.add(actor);
        this.movieRepository.save(movie);

        Collection<Artist> actorsToAdd = this.actorsToAdd(movie.getId());


        return actorsToAdd;
    }


    @Transactional
    public Collection<Artist> removeActorFromMovie(Movie movie, Artist actor){

        Set<Artist> actors = movie.getActors();
        actors.remove(actor);
        this.movieRepository.save(movie);

        Collection<Artist> actorsToAdd = actorsToAdd(movie.getId());

        return actorsToAdd;
    }

    @Transactional
    public Movie saveMovie(Movie movie){
        return this.movieRepository.save(movie);
    }



    @Transactional
    public Collection<Artist> actorsToAdd( Long movieId){

        List<Artist> actorsToAdd = new ArrayList<>();

        for (Artist a : this.artistService.getActorsNotInMovie(movieId)) {
            actorsToAdd.add(a);
        }
        return actorsToAdd;
    }

    @Transactional
    public void createMovie(Movie movie, Long directorToAddId, Collection<Long> actorsToaddId,
                            MultipartFile multipartFile,  Collection<MultipartFile> scenes) {

        movie.setDirector(this.artistService.getArtist(directorToAddId));

        try {
            this.addMoviePhoto(multipartFile, movie);
            this.addMovieScenes(scenes, movie);
        }catch(IOException e){

        }

        Set<Artist> actors = new HashSet<>();

        for(Long actorId : actorsToaddId){

             actors.add(this.artistService.getArtist(actorId));
             movie.setActors(actors);
        }

        this.movieRepository.save(movie);
    }

    @Transactional
    private Movie addMovieScenes(Collection<MultipartFile> scenes, Movie movie) throws IOException {

     Set<Image>  movieScenes  = new HashSet<>();

     for(MultipartFile scene : scenes){


         movieScenes.add(imageRepository.save(new Image(scene.getBytes())));

     }


        movie.setScenes(movieScenes);


        this.saveMovie(movie);


        return movie;
    }


    @Transactional
    public Movie addMoviePhoto(MultipartFile multipartFile, Movie movie) throws IOException {


        movie.setImage(imageRepository.save(new Image(multipartFile.getBytes())));

        this.saveMovie(movie);


        return movie;
    }

    public Movie removeDirectorFromMovie(Long idMovie) {

        Movie movie = this.getMovie(idMovie);

        movie.setDirector(null);

        this.movieRepository.save(movie);

        return movie;
    }


    public Movie addReviewToMovie(Review review, Long idMovie){

        Movie movie = this.getMovie(idMovie);
        User loggedUser = this.sessionData.getLoggedUser();

        review.setAuthor(loggedUser);
        review.setReviewedMovie(movie);

        this.reviewRepository.save(review);
        this.movieRepository.save(movie);


        return movie;
    }


    @Transactional
    public Collection<Movie> getMoviesReviewdByUser(User user){

        return this.movieRepository.findMoviesNotReviewedByUser(user);
    }


    @Transactional
    public Collection<Movie> getMoviesReleasedInLast30Days(){

        LocalDate currentTime = LocalDate.now();

        LocalDate startDate = currentTime.minusDays(30);

        return this.movieRepository.findMoviesReleasedInLast30Days(startDate);
    }


    @Transactional
    public Collection<MergeMovieObject> getMovieOrderByAverageRating(){

        Collection<Object[]> objects =  this.movieRepository.findMoviesOrderByAverageRatingWithCountAndAvgRating();

        Collection<MergeMovieObject> movieObjects = new ArrayList<>();

        for( Object[] object:  objects){

            movieObjects.add(new MergeMovieObject( (Movie)object[0],(Long)object[1],(Double)object[2]));

        }

        return movieObjects;

    }

    @Transactional
    public  Collection<MergeMovieObject> getMoviesOrderByMostReviews(){

        Collection<Object[]> objects = this.movieRepository.findMoviesOrderByMostReviewsWithCountAndAvgRating();

        Collection<MergeMovieObject> movieObjects = new ArrayList<>();

        for( Object[] object: objects ){

            movieObjects.add(new MergeMovieObject( (Movie)object[0],(Long)object[1],(Double)object[2]));
        }


        return movieObjects;
    }
}

