package it.uniroma3.siw.hz.controller;

import java.util.*;


import it.uniroma3.siw.hz.controller.session.SessionData;
import it.uniroma3.siw.hz.controller.validator.MovieValidator;
import it.uniroma3.siw.hz.model.*;
import it.uniroma3.siw.hz.repository.ArtistRepository;
import it.uniroma3.siw.hz.repository.MovieRepository;
import it.uniroma3.siw.hz.service.ArtistService;
import it.uniroma3.siw.hz.service.CredentialsService;
import it.uniroma3.siw.hz.service.MovieService;
import jakarta.persistence.PreUpdate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class MovieController {
	@Autowired 
	private MovieService movieService;
	
	@Autowired 
	private ArtistService artistService;

	@Autowired 
	private MovieValidator movieValidator;



	@GetMapping(value="/admin/formNewMovie")
	public String formNewMovie(Model model) {
		model.addAttribute("movie", new Movie());
		model.addAttribute("artists",this.artistService.getAllArtists());
		return "admin/formNewMovie.html";
	}

	@GetMapping(value="/admin/formUpdateMovie/{id}")
	public String formUpdateMovie(@PathVariable("id") Long id, Model model) {
		model.addAttribute("movie", this.movieService.getMovie(id));
		return "admin/formUpdateMovie.html";
	}

	@GetMapping(value="/admin/indexMovie")
	public String indexMovie() {
		return "admin/indexMovie.html";
	}
	
	@GetMapping(value="/admin/manageMovies")
	public String manageMovies(Model model) {
		model.addAttribute("movies", this.movieService.getAllMovies());
		return "admin/manageMovies.html";
	}
	
	@GetMapping(value="/admin/setDirectorToMovie/{directorId}/{movieId}")
	public String setDirectorToMovie(@PathVariable("directorId") Long directorId, @PathVariable("movieId") Long movieId, Model model) {

		model.addAttribute("movie", this.movieService.setDirectorToMovie(directorId, movieId));
		return "movie.html";
	}
	
	
	@GetMapping(value="/admin/addDirector/{id}")
	public String addDirector(@PathVariable("id") Long id, Model model) {

		Movie movie = this.movieService.getMovie(id);
		model.addAttribute("notMovieDirectors", this.artistService.getArtistsNotDirectingMovie(movie));
		model.addAttribute("movie",  movie);

		return "admin/directorsToAdd.html";
	}

	@PostMapping("/admin/movie")
	public String newMovie(@Valid @ModelAttribute("movie") Movie movie, BindingResult bindingResult,
						   @RequestParam( value = "movieImage",required = false) MultipartFile multipartFile,
						   @RequestParam(value = "directorsToAdd",required = false) Long directorToAddId,
						   @RequestParam(value = "actorsToAdd",required = false) Collection<Long> actorsToaddId,
						   @RequestParam(value = "movieScenes",required = false) Collection<MultipartFile> scenes

			, Model model) {
		
		this.movieValidator.validate(movie, bindingResult);
		  if (!bindingResult.hasErrors()) {

			this.movieService.createMovie(movie,directorToAddId,actorsToaddId,multipartFile,scenes);

			model.addAttribute("movie", movie);

			return "movie.html";
		} else {


			return "admin/formNewMovie.html";

		}
	}

	@GetMapping("/movie/{id}")
	public String getMovie(@PathVariable("id") Long id, Model model) {
		model.addAttribute("movie", this.movieService.getMovie(id));
		return "movie.html";
	}

	@GetMapping("/movie")
	public String getMovies(Model model) {
		
    /*	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());*/

		model.addAttribute("movies", this.movieService.getAllMovies());
		/*model.addAttribute("user", credentials.getUser());*/
		return "movies.html";
	}
	
	@GetMapping("/formSearchMovies")
	public String formSearchMovies() {
		return "formSearchMovies.html";
	}
/*
	@PostMapping("/searchMovies")
	public String searchMovies(Model model, @RequestParam int year) {
		model.addAttribute("movies", this.movieService.getMoviesByYear(year));
		return "foundMovies.html";
	}*/
	
	@GetMapping("/admin/updateActors/{id}")
	public String updateActors(@PathVariable("id") Long id, Model model) {


		Movie movie = this.movieService.getMovie(id);
		Collection<Artist> actorsToAdd = this.actorsToAdd(id);
		model.addAttribute("actorsToAdd", actorsToAdd);
		model.addAttribute("movieActors",movie.getActors());
		model.addAttribute("movie",movie );

		return "admin/actorsToAdd.html";
	}

	@GetMapping(value="/admin/addActorToMovie/{actorId}/{movieId}")
	public String addActorToMovie(@PathVariable("actorId") Long actorId, @PathVariable("movieId") Long movieId, Model model) {

		Movie movie = this.movieService.getMovie(movieId);
		Artist actor = this.artistService.getArtist(actorId);


		Collection<Artist> actorsToAdd = 	this.movieService.addActorToMovie(movie,actor);
		
		model.addAttribute("movie", movie);
		model.addAttribute("movieActors", movie.getActors());
		model.addAttribute("actorsToAdd",actorsToAdd );

		return "movie.html";
	}
	
	@GetMapping(value="/admin/removeActorFromMovie/{actorId}/{movieId}")
	public String removeActorFromMovie(@PathVariable("actorId") Long actorId, @PathVariable("movieId") Long movieId, Model model) {
		Movie movie = this.movieService.getMovie(movieId);
		Artist actor = this.artistService.getArtist(actorId);

		Collection<Artist> actorsToAdd = 	this.movieService.removeActorFromMovie(movie,actor);

		model.addAttribute("movie", movie);

		return "movie.html";
	}

	private Collection<Artist> actorsToAdd(Long movieId) {
             return this.movieService.actorsToAdd(movieId);
}


     @RequestMapping(value = {"/removeDirector/{idM}"}, method = RequestMethod.GET)
	public String removeDirectorFromMovie(Model model, @PathVariable("idM") Long idMovie){


		model.addAttribute("movie",this.movieService.removeDirectorFromMovie(idMovie) );
		return "movie.html";

	 }

	 @RequestMapping(value={"/addReview/{idM}"}, method = RequestMethod.GET)
	 public String addReviewToMovie(@PathVariable("idM") Long idMovie,Model model){

		model.addAttribute("movie", this.movieService.getMovie(idMovie));
		return "addReview.html";
	 }

	 @RequestMapping(value={"/addReview/{idM}"}, method = RequestMethod.POST)
	public String addReviewToMovie(@ModelAttribute Review review,@PathVariable("idM") Long idMovie,Model model){


		model.addAttribute("movie",this.movieService.addReviewToMovie(review,idMovie));

		return "movie.html";
	 }


}
