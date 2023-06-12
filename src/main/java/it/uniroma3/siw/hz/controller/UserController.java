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
import org.springframework.beans.propertyeditors.ReaderEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @RequestMapping(value = {"/addUserPicture/{id}"}, method = RequestMethod.POST)
    public String addUserPhoto(@Valid @ModelAttribute FileUploadWrapper fileUploadWrapper,
                               BindingResult fileUploadWrapperBindingResult, @PathVariable("id")
                               Long id, RedirectAttributes  redirectAttributes) {

        this.multipartFileValidator.validate(fileUploadWrapper, fileUploadWrapperBindingResult);


        if (!fileUploadWrapperBindingResult.hasErrors()) {


            try {
                this.userService.addUserPicture(id, fileUploadWrapper.getImage());

                redirectAttributes.addFlashAttribute("registrated", true);

                return "redirect:/";
            }catch (IOException e){

                redirectAttributes.addFlashAttribute("fileUploadError","An error occured while uploading the input files");
                return "registrationSuccessfull.html";
            }


        } else {


            return "registrationSuccessfull.html";
        }


    }

}