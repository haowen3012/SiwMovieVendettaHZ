package it.uniroma3.siw.hz.controller;


import it.uniroma3.siw.hz.FileUploadUtil;
import it.uniroma3.siw.hz.controller.session.SessionData;
import it.uniroma3.siw.hz.model.Image;
import it.uniroma3.siw.hz.model.User;
import it.uniroma3.siw.hz.repository.ImageRepository;
import it.uniroma3.siw.hz.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class UserController {


    @Autowired
    private SessionData sessionData;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageRepository imageRepository;

    @Transactional
    @RequestMapping(value={"/addUserPhoto/{id}"}, method = RequestMethod.POST)
    public String addUserPhoto(@RequestParam("image")  MultipartFile multipartFile,@PathVariable("id")
                               Long id) throws IOException {

        User user = this.userService.getUser(id);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        user.setPicture(imageRepository.save(new Image(fileName)));

        this.userService.saveUser(user);

        String uploadDir = "files/userFiles/" + user.getId();


        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return "index.html";
    }
}
