package it.uniroma3.siw.hz.service;

import it.uniroma3.siw.hz.model.Artist;
import it.uniroma3.siw.hz.model.Movie;
import it.uniroma3.siw.hz.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;


    @Autowired
    private ArtistService artistService;



    @Transactional
    public Movie getMovie(Long id){
        return this.movieRepository.findById(id).get();
    }


    @Transactional
    public Collection<Movie> getAllMovies(){
        return (Collection<Movie>) this.movieRepository.findAll();

    }


    @Transactional
    public Collection<Movie> getMoviesByYear(int year){

        return this.movieRepository.findByYear(year);
    }


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

}
