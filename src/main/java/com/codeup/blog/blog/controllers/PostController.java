package com.codeup.blog.blog.controllers;

import com.codeup.blog.blog.models.Category;
import com.codeup.blog.blog.models.User;
import com.codeup.blog.blog.repositories.CategoryRepository;
import com.codeup.blog.blog.repositories.PostRepository;
import com.codeup.blog.blog.models.Post;
import com.codeup.blog.blog.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;


@Controller
public class PostController {

    private final PostRepository postDao;
    private final UserRepository userDao;
    private final CategoryRepository categoryDao;


    public PostController(PostRepository postDao, UserRepository userDao, CategoryRepository categoryDao) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.categoryDao = categoryDao;
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/posts")
    public String index(Model viewModel) {
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Post> posts = userDao.getOne(loggedUser.getId()).getPosts();
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
        vModel.addAttribute("categories", categoryDao.findAll());
        return "/posts/create";
    }

    @PostMapping("/posts/create")
    public String create(@ModelAttribute Post newPost, @RequestParam(value = "categories", required = false) int[] categories, Model vModel) {
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newPost.setUser(loggedUser);
        Category category;

//        System.out.println("categories = " + categories);
//        Get the categories
        if (categories != null) {
            List<Category> newCategories = new ArrayList<>();

            for (int i = 0; i < categories.length; i++) {
                System.out.println("categories[i] = " + categories[i]);
                category = categoryDao.getOne((long) categories[i]);
                newCategories.add(category);
            }
            newPost.setCategories(newCategories);
        }
        postDao.save(newPost);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/historyOfPost")
    public String showHistoryPost(@PathVariable long id, Model vModel) {
        Post post = postDao.getOne(id);
        System.out.println("post.getId() = " + post.getId());
        vModel.addAttribute("postHistory", post.getPostDetails().getHistoryOfPost());
        return "/posts/historyOfPost";
    }

    @GetMapping("/posts/{id}/update")
    public String updatePost(@PathVariable long id, Model viewModel) {
        viewModel.addAttribute("post", postDao.getOne(id));
        List<Category> listCategories = postDao.getOne(id).getCategories();
        System.out.println("listCategories = " + listCategories);
        if (! listCategories.isEmpty()) {
            viewModel.addAttribute("categories", postDao.getOne(id).getCategories());
        } else {
            viewModel.addAttribute("categories", categoryDao.findAll());
        }
        return "posts/update";
    }

    @PostMapping("/posts/{id}/update")
    public String update(@PathVariable long id, @RequestParam String title, @RequestParam String body, @RequestParam(value = "categories", required = false) int[] categories ) {
        Post oldPost = postDao.getOne(id);
        Category category;
        oldPost.setTitle(title);
        oldPost.setBody(body);
        if (categories != null) {
            List<Category> newCategories = new ArrayList<>();

            for (int i = 0; i < categories.length; i++) {
//                System.out.println("categories[i] = " + categories[i]);
                category = categoryDao.getOne((long) categories[i]);
                newCategories.add(category);
            }
            oldPost.setCategories(newCategories);
        }
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
