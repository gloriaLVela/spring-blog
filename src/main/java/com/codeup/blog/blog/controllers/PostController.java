package com.codeup.blog.blog.controllers;

import com.codeup.blog.blog.Repositories.PostRepository;
import com.codeup.blog.blog.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class PostController {

    private final PostRepository postDao ;

    public PostController(PostRepository postDao) {
        this.postDao = postDao;
    }


    @GetMapping("/posts")
    public String index(Model viewModel) {

        viewModel.addAttribute("posts", postDao.findAll());

        return "posts/index";
    }


    @GetMapping("/posts/{id}")
    public String show(@PathVariable long id, Model viewModel) {
        viewModel.addAttribute("post", postDao.getOne(id));
        return "posts/show";
    }


    @GetMapping( "/posts/create")
    public String showCreatePost(Model vModel) {
        vModel.addAttribute("post", new Post());
        return "/posts/create";
    }

    @PostMapping("/posts/create")
    public String create(@ModelAttribute Post post) {
        postDao.save(post);
        return "redirect:/posts";
    }

    @GetMapping( "/posts/{id}/update")
    public String updatePost(@PathVariable long id, Model viewModel) {
        viewModel.addAttribute("post", postDao.getOne(id));
        return "posts/update";
    }

    @PostMapping("/posts/{id}/update")
    public String update( @PathVariable long id, @RequestParam String title, @RequestParam String body) {
        Post oldPost = postDao.getOne(id);
        oldPost.setTitle(title);
        oldPost.setBody(body);
        postDao.save(oldPost);
        return "redirect:/posts/" + id;
    }



    @PostMapping("/posts/{id}/delete")
    public String delete(@PathVariable long id) {
//        System.out.println("id = " + id);
        postDao.deleteById(id);
        return "redirect:/posts";
    }

}
