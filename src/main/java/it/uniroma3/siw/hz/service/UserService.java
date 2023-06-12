package it.uniroma3.siw.hz.service;

import it.uniroma3.siw.hz.controller.validator.MultipartFileValidator;
import it.uniroma3.siw.hz.model.Image;
import it.uniroma3.siw.hz.model.User;
import it.uniroma3.siw.hz.oauth.AuthenticationProvider;
import it.uniroma3.siw.hz.repository.ImageRepository;
import it.uniroma3.siw.hz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The UserService handles logic for Users.
 */
@Service
public class UserService {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    /**
     * This method retrieves a User from the DB based on its ID.
     * @param id the id of the User to retrieve from the DB
     * @return the retrieved User, or null if no User with the passed ID could be found in the DB
     */
    @Transactional
    public User getUser(Long id) {
        Optional<User> result = this.userRepository.findById(id);
        return result.orElse(null);
    }


    @jakarta.transaction.Transactional
    public User getUser(String username){
        Optional<User> result = this.userRepository.findByUserName(username);
        return result.orElse(null);
    }

    /**
     * This method saves a User in the DB.
     * @param user the User to save into the DB
     * @return the saved User
     * @throws DataIntegrityViolationException if a User with the same username
     *                              as the passed User already exists in the DB
     */
    @Transactional
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    /**
     * This method retrieves all Users from the DB.
     * @return a List with all the retrieved Users
     */
    @Transactional
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        Iterable<User> iterable = this.userRepository.findAll();
        for(User user : iterable)
            result.add(user);
        return result;
    }


    @Transactional
    public  void addUserPicture(Long idUser, MultipartFile picture){

        User user = this.getUser(idUser);

        try {
            user.setPicture(   this.imageRepository.save(new Image(picture.getOriginalFilename(), picture.getBytes())));

        }catch(IOException e){

        }

        this.saveUser(user);

    }

    /*******************OAuth2********************************************+*/
    /**
     *
     * @param loginName
     * @param fullName
     * @param provider
     */
    public void registerNewCustomerAfterOAuthLoginSuccess(String loginName, String fullName, AuthenticationProvider provider) {
        User user = new User();
        if(loginName != null) {
            user.setName(loginName);
        }
        else{
            user.setName(fullName);
        }

        user.setCreationTimestamp(LocalDateTime.now());
        user.setoAuthProvider(provider);


        userRepository.save(user);
    }

    public void updateExistingUser(User user, String fullName, AuthenticationProvider provider){
        user.setName(fullName);
        user.setoAuthProvider(provider);   // probabilmente da modificare

        userRepository.save(user);
    }
}
