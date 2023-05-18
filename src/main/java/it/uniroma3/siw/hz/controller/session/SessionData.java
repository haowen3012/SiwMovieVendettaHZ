package it.uniroma3.siw.hz.controller.session;


import it.uniroma3.siw.hz.model.Credentials;
import it.uniroma3.siw.hz.model.User;
import it.uniroma3.siw.hz.repository.CredentialsRepository;
import it.uniroma3.siw.hz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {

    private User user;

    private Credentials credentials;

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private UserRepository userRepository;


    public Credentials getLoggedCredentials(){

        this.update();

        return this.credentials;
    }

    public User getLoggedUser(){

            this.update();



        return this.user;
    }

/*throe class casta exception tolta, forse è da rimettere*/

    private void update() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails loggedUserDetails = (UserDetails) object;

        this.credentials = this.credentialsRepository.findByUsername(loggedUserDetails.getUsername()).get();
        this.credentials.setPassword("[PROTECTED]");
        this.user = this.credentials.getUser();


    }

}
