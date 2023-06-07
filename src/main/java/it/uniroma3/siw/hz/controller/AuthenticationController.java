package it.uniroma3.siw.hz.controller;


import it.uniroma3.siw.hz.controller.session.SessionData;
import it.uniroma3.siw.hz.controller.validator.CredentialsValidator;
import it.uniroma3.siw.hz.controller.validator.UserValidator;
import it.uniroma3.siw.hz.model.Credentials;
import it.uniroma3.siw.hz.model.MergeMovieObject;
import it.uniroma3.siw.hz.model.Movie;
import it.uniroma3.siw.hz.model.User;
import it.uniroma3.siw.hz.service.CredentialsService;
import it.uniroma3.siw.hz.service.MovieService;
import jakarta.validation.Valid;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;


	@Autowired
	private UserValidator userValidator;

	@Autowired
	private CredentialsValidator credentialsValidator;

	@Autowired
	private MovieService movieService;

	@Autowired
	private SessionData sessionData;
	
	@GetMapping(value = "/register")
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "formRegisterUser";
	}
/*
	@GetMapping(value = "/login")
	public String showLoginForm (Model model) {
		return "index.html";
	}*/

	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String index(Model model) {

		model.addAttribute("newMovies", this.movieService.getMoviesReleasedInLast30Days());

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			model.addAttribute("userForm", new User());
			model.addAttribute("credentialsForm", new Credentials());


		}
		else {
			try {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
				if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
					return "admin/indexAdmin.html";
				}
			}catch(ClassCastException e ){

				return "index.html";
			}
		}

        return "index.html";
	}
		
    @GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "admin/indexAdmin.html";
        }
        return "index.html";
    }

	@PostMapping(value = { "/register" })
    public String registerUser(@Valid @ModelAttribute("userForm") User user,
                 BindingResult userBindingResult, @Valid
                 @ModelAttribute("credentialsForm") Credentials credentials,
                 BindingResult credentialsBindingResult,
                 Model model) {
		//validate user and credentials fields
		this.userValidator.validate(user, userBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);

        // se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
        if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
			model.addAttribute("user" ,user);
            return "registrationSuccessful.html";
        }
        return "index.html";
    }


	@RequestMapping(value={"login/oauth2/user"}, method = RequestMethod.GET)
	public String oAuth2Successful(){


		User loggedUser = this.sessionData.getLoggedUser();

		if(loggedUser.getPicture()!=null ){

			return "redirect:/";
		}


		return "registrationSuccessful.html";
	}
}