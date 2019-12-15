package com.codeup.blog.blog.controllers;

import com.codeup.blog.blog.models.Category;
import com.codeup.blog.blog.models.User;
import com.codeup.blog.blog.repositories.CategoryRepository;
import com.codeup.blog.blog.repositories.PostRepository;
import com.codeup.blog.blog.models.Post;
import com.codeup.blog.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Null;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;

@Controller
public class PostController {

    private final PostRepository postDao;
    private final UserRepository userDao;
    private final CategoryRepository categoryDao;

    @Value("${file-upload-path}")
    private String uploadPath;

    public PostController(PostRepository postDao, UserRepository userDao, CategoryRepository categoryDao) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.categoryDao = categoryDao;
    }

    private void addCategoriesPost(int[] categories, Post currentPost) {
        // Get the categories
        Category category;
        if (categories != null) {
            List<Category> newCategories = new ArrayList<>();

            for (int i = 0; i < categories.length; i++) {
                category = categoryDao.getOne((long) categories[i]);
                newCategories.add(category);
            }
            currentPost.setCategories(newCategories);
        }
        return;
    }

    @GetMapping("/home")
    public String home(Model viewModel) {
        List<User> users = userDao.findAll();
        List<Post> posts = postDao.findAll();
        Post currentPost = new Post();


        for (User currentUser : users) {
           posts.add( userDao.findByUsername(currentUser.getUsername()).getPosts().get(0));
        }

        viewModel.addAttribute("users", users);
        return "home";

    }

    @GetMapping("/posts")
    public String index(Model viewModel) {

        List<Post> posts = postDao.findAll();
        for (Post currentPost : posts) {
            currentPost.getCategories();
        }

        viewModel.addAttribute("posts", posts);
        return "posts/index";
    }


    @GetMapping("/posts/{id}")
    public String show(@PathVariable long id, Model viewModel) {
        viewModel.addAttribute("post", postDao.getOne(id));
        viewModel.addAttribute("categories", postDao.getOne(id).getCategories());
        return "posts/show";
    }


    @GetMapping("/posts/create")
    public String showCreatePost(Model vModel) {
        vModel.addAttribute("post", new Post());
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        vModel.addAttribute("categories", userDao.findByUsername(loggedUser.getUsername()).getCategories());
        return "/posts/create";
    }

    @PostMapping("/posts/create")
    public String create(@ModelAttribute Post newPost, @RequestParam(value = "categories", required = false) int[] categories, Model vModel) {
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newPost.setUser(loggedUser);
        addCategoriesPost(categories, newPost);
        postDao.save(newPost);
        return "redirect:/myPosts";
    }

    @GetMapping("/posts/{id}/historyOfPost")
    public String showHistoryPost(@PathVariable long id, Model vModel) {
        Post post = postDao.getOne(id);
        vModel.addAttribute("postHistory", post.getPostDetails().getHistoryOfPost());
        return "/posts/historyOfPost";
    }

    @GetMapping("/posts/{id}/update")
    public String updatePost(@PathVariable long id, Model viewModel) {
//        System.out.println("Post update");
        int index = 0;
        viewModel.addAttribute("post", postDao.getOne(id));
        List<Category> listCategories = postDao.getOne(id).getCategories();
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!listCategories.isEmpty()) {
            List<Category> userCategories = postDao.getOne(id).getCategories();
            List<Category> availableCategories = userDao.findByUsername(loggedUser.getUsername()).getCategories();
            for (int i = 0; i < userCategories.size(); i++) {
                index = availableCategories.indexOf(userCategories.get(i));
                if (index >= 0) {
                    availableCategories.remove(index);
                }
            }
            viewModel.addAttribute("categories", userCategories);
            viewModel.addAttribute("newCategories", availableCategories);
        } else {

            viewModel.addAttribute("categories", userDao.findByUsername(loggedUser.getUsername()).getCategories());
        }
        return "posts/update";

    }

    @PostMapping("/posts/{id}/update")
    public String update(@PathVariable long id,
                         @RequestParam String title,
                         @RequestParam String body,
                         @RequestParam(value = "categories",
                                 required = false) int[] categories) {
        Post oldPost = postDao.getOne(id);
        Category category;
        oldPost.setTitle(title);
        oldPost.setBody(body);
        addCategoriesPost(categories, oldPost);
        postDao.save(oldPost);
        return "redirect:/myPosts";
    }

//    @PostMapping("/posts/{id}/pictureUpload")
//    public String saveFile(
//            @PathVariable long id,
//            @RequestParam(name = "file") MultipartFile uploadedPicture,
//            Model model)
//    {
//        Post currentPost;
//        String filename = uploadedPicture.getOriginalFilename();
//        var is = uploadedPicture.getInputStream();
//
//            Files.copy(is, Paths.get(path + fileName),
//            StandardCopyOption.REPLACE_EXISTING);
////        String filepath = Paths.get(uploadPath, filename).toString();
//        File destinationFile = new File(filepath);
//        try {
//            System.out.println("destinationFile = " + destinationFile);
//            uploadedPicture.transferTo(destinationFile);
//            currentPost = postDao.getOne(id);
//            currentPost.setPicture_url(uploadedPicture.getOriginalFilename());
//            System.out.println("uploadedPicture.getOriginalFilename() = " + uploadedPicture.getOriginalFilename());
//            postDao.save(currentPost);
//            return  "redirect:/posts/" + id + "/update";
//        } catch (IOException e) {
//
//            e.printStackTrace();
//            model.addAttribute("message", "Oh no, Oops! Something went wrong! " + e);
//        }
//        return "redirect:/posts/" + id + "/update";
//    }

    @PostMapping("/posts/{id}/delete")
    public String delete(@PathVariable long id) {
//        System.out.println("id = " + id);
        postDao.deleteById(id);
        return "redirect:/posts";
    }


}
