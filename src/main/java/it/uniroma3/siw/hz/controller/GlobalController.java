package it.unicollab.bh.controller;

import it.unicollab.bh.controller.session.SessionData;
import it.unicollab.bh.model.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public  class GlobalController
{

    @Autowired
    private  SessionData sessionData;

    @ModelAttribute("user")
    public  User getUser(){

        try {
            return this.sessionData.getLoggedUser();
        }
        catch(ClassCastException e){
            return null;
        }
    }
}
