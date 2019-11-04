package com.codeup.blog.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    // Fix it
    @RequestMapping(path = "/posts", method = RequestMethod.GET)
    @ResponseBody
    public String postIndexPage() {
        return "posts index page";
    }

    @RequestMapping(path = "/posts/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String postIndividualPost(@PathVariable String id) {
        return "view an individual post with the id " + id;
    }


    @RequestMapping(path = "/posts/create", method = RequestMethod.GET)
    @ResponseBody
    public String viewIndividualPost() {
        return "view the form for creating a post";
    }

    @PostMapping(path = "/posts/create")
    @ResponseBody
    public String createNewPost() {
        System.out.println("create a new post");
        return "create a new post";
    }

}
