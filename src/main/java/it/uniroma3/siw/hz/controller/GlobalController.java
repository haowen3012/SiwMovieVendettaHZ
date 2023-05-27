package it.uniroma3.siw.hz.controller;


import it.uniroma3.siw.hz.controller.session.SessionData;
import it.uniroma3.siw.hz.model.Credentials;
import it.uniroma3.siw.hz.model.User;
import it.uniroma3.siw.hz.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public  class GlobalController
{

    @Autowired
    private SessionData sessionData;

    @Autowired
    private CredentialsService credentialsService;

    @ModelAttribute("user")
    public User getUser(){

        try {
            return this.sessionData.getLoggedUser();
        }
        catch(ClassCastException e){
            return null;
        }
    }

    @ModelAttribute("role")
    public String getUserRole(){
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());

            return credentials.getRole();
        }catch(ClassCastException e){
            return null;
        }
    }
}
