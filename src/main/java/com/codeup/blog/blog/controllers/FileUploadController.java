package com.codeup.blog.blog.controllers;

import com.codeup.blog.blog.models.Post;
import com.codeup.blog.blog.models.User;
import com.codeup.blog.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class FileUploadController {

    private final UserRepository userDao;

    public FileUploadController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @Value("${file-upload-path}")
    private String uploadPath;

    @GetMapping("/fileupload")
    public String showUploadFileForm() {

        //System.out.println("upload");
        return "fileupload";
    }

    @PostMapping("/fileupload")
    public String saveFile(
            @RequestParam(name = "file") MultipartFile uploadedFile,
            Model model
    ) {
        //System.out.println("after save postback ");
        String filename = uploadedFile.getOriginalFilename();
        String filepath = Paths.get(uploadPath, filename).toString();
        File destinationFile = new File(filepath);
        try {
            uploadedFile.transferTo(destinationFile);
            User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User currentUser = userDao.getOne(loggedUser.getId());
            currentUser.setBlog_image(filepath);
            userDao.save(currentUser);
            return  "redirect:/myPosts";
            //model.addAttribute("message", "File successfully uploaded!");
        } catch (IOException e) {

            e.printStackTrace();
            model.addAttribute("message", "Oh no, Oops! Something went wrong! " + e);
        }
        return "redirect:/fileupload";
    }
}