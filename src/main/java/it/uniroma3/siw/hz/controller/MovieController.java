package it.uniroma3.siw.hz.controller;

import java.util.*;


import it.uniroma3.siw.hz.controller.validator.MovieValidator;
import it.uniroma3.siw.hz.model.Artist;
import it.uniroma3.siw.hz.model.Credentials;
import it.uniroma3.siw.hz.model.Movie;
import it.uniroma3.siw.hz.repository.ArtistRepository;
import it.uniroma3.siw.hz.repository.MovieRepository;
import it.uniroma3.siw.hz.service.ArtistService;
import it.uniroma3.siw.hz.service.CredentialsService;
import it.uniroma3.siw.hz.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class MovieController {
	@Autowired 
	private MovieService movieService;
	
	@Autowired 
	private ArtistService artistService;

	@Autowired 
	private MovieValidator movieValidator;
	
	@Autowired 
	private CredentialsService credentialsService;

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
		return "admin/formUpdateMovie.html";
	}
	
	
	@GetMapping(value="/admin/addDirector/{id}")
	public String addDirector(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artists", this.artistService.getAllArtists());
		model.addAttribute("movie",  this.movieService.getMovie(id));
		return "admin/directorsToAdd.html";
	}

	@PostMapping("/admin/movie")
	public String newMovie(@Valid @ModelAttribute("movie") Movie movie, BindingResult bindingResult,
						   @RequestParam("directorsToAdd") Long directorToAddId,
						   @RequestParam("actorsToAdd") Collection<Long> actorsToaddId,
						   @RequestParam("image")MultipartFile multipartFile

			, Model model) {
		
		this.movieValidator.validate(movie, bindingResult);
		if (!bindingResult.hasErrors()) {


			this.movieService.createMovie(movie,directorToAddId,actorsToaddId,multipartFile);

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

		Collection<Artist> actorsToAdd = this.actorsToAdd(id);
		model.addAttribute("actorsToAdd", actorsToAdd);
		model.addAttribute("movie", this.movieService.getMovie(id));

		return "admin/actorsToAdd.html";
	}

	@GetMapping(value="/admin/addActorToMovie/{actorId}/{movieId}")
	public String addActorToMovie(@PathVariable("actorId") Long actorId, @PathVariable("movieId") Long movieId, Model model) {

		Movie movie = this.movieService.getMovie(movieId);
		Artist actor = this.artistService.getArtist(actorId);


		Collection<Artist> actorsToAdd = 	this.movieService.addActorToMovie(movie,actor);
		
		model.addAttribute("movie", movie);
		model.addAttribute("actorsToAdd",actorsToAdd );

		return "admin/actorsToAdd.html";
	}
	
	@GetMapping(value="/admin/removeActorFromMovie/{actorId}/{movieId}")
	public String removeActorFromMovie(@PathVariable("actorId") Long actorId, @PathVariable("movieId") Long movieId, Model model) {
		Movie movie = this.movieService.getMovie(movieId);
		Artist actor = this.artistService.getArtist(actorId);

		Collection<Artist> actorsToAdd = 	this.movieService.removeActorFromMovie(movie,actor);

		model.addAttribute("movie", movie);
		model.addAttribute("actorsToAdd", actorsToAdd);

		return "admin/actorsToAdd.html";
	}

	private Collection<Artist> actorsToAdd(Long movieId) {
             return this.movieService.actorsToAdd(movieId);
}

}
