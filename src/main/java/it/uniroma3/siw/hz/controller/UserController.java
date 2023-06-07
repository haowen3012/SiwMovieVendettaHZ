package it.uniroma3.siw.hz.controller;


import it.uniroma3.siw.hz.FileUploadWrapper;
import it.uniroma3.siw.hz.controller.session.SessionData;
import it.uniroma3.siw.hz.controller.validator.MultipartFileValidator;
import it.uniroma3.siw.hz.model.Image;
import it.uniroma3.siw.hz.model.User;
import it.uniroma3.siw.hz.repository.ImageRepository;
import it.uniroma3.siw.hz.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class UserController {


    @Autowired
    private SessionData sessionData;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageRepository imageRepository;


    @Autowired
    private MultipartFileValidator multipartFileValidator;

    @Transactional
    @RequestMapping(value={"/addUserPhoto/{id}"}, method = RequestMethod.POST)
    public String addUserPhoto(@Valid @ModelAttribute FileUploadWrapper fileUploadWrapper,
                               BindingResult fileUploadWrapperBindingResult , @PathVariable("id")
                               Long id) throws IOException {

        this.multipartFileValidator.validate(fileUploadWrapper, fileUploadWrapperBindingResult);


        if (!fileUploadWrapperBindingResult.hasErrors()){

            this.userService.addUserPicture(id, file);
            User user = this.userService.getUser(id);

        user.setPicture(imageRepository.save(new Image(multipartFile.getName(), multipartFile.getBytes())));

        this.userService.saveUser(user);

        return "index.html";
    }



    }
}
