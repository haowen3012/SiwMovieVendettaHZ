package it.uniroma3.siw.hz.service;

import com.nimbusds.oauth2.sdk.id.Actor;
import it.uniroma3.siw.hz.FileUploadUtil;
import it.uniroma3.siw.hz.model.Artist;
import it.uniroma3.siw.hz.model.Movie;
import it.uniroma3.siw.hz.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.hibernate.internal.util.MutableLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public void createMovie(Movie movie, Long directorToAddId, Collection<Long> actorsToaddId, MultipartFile multipartFile) {

        movie.setDirector(this.artistService.getArtist(directorToAddId));

        try {
            this.addMoviePhoto(multipartFile, movie);
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
    public Movie addMoviePhoto(MultipartFile multipartFile, Movie movie) throws IOException {


        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        movie.setImage(fileName);

        this.saveMovie(movie);

        String uploadDir = "files/movieFiles/" + movie.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return movie;
    }
}
