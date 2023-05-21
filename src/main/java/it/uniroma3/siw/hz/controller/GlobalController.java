package it.uniroma3.siw.hz.controller;


import it.uniroma3.siw.hz.controller.session.SessionData;
import it.uniroma3.siw.hz.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public  class GlobalController
{

    @Autowired
    private SessionData sessionData;

    @ModelAttribute("user")
    public User getUser(){

        try {
            return this.sessionData.getLoggedUser();
        }
        catch(ClassCastException e){
            return null;
        }
    }
}
