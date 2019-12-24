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
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    private final PostRepository postDao;
    private final UserRepository userDao;
    private final CategoryRepository categoryDao;

    @Value("${file-upload-path}")
    private String uploadPath;

    public PostController(PostRepository postDao,
                          UserRepository userDao,
                          CategoryRepository categoryDao) {
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
//        List<User> users = userDao.findAll();
//        List<Post> posts = postDao.findAll();
//        Post currentPost = new Post();
//
//
//        for (User currentUser : users) {
//           posts.add( userDao.findByUsername(currentUser.getUsername()).getPosts().get(0));
//        }
//
//        viewModel.addAttribute("users", users);
        return "home";

    }

    @GetMapping("/featured")
    public String featuredPost(Model viewModel) {
        List<User> users = userDao.findAll();
        List<Post> posts = postDao.findAll();
//        Post currentPost = new Post();
        for (User currentUser : users) {
            posts.add( userDao.findByUsername(currentUser.getUsername()).getPosts().get(0));
        }

        viewModel.addAttribute("users", users);
        return "posts/featured";
    }

    @GetMapping("/showBlog/{id}")
    public String listPosts(@PathVariable long id, Model viewModel) {
        User selectedBlog = userDao.getOne(id);
        List<Post> posts = selectedBlog.getPosts();
        for (Post currentPost : posts) {
            currentPost.getCategories();
        }
        viewModel.addAttribute("user", selectedBlog);
        viewModel.addAttribute("posts", posts);
        return "posts/index";
    }



    @GetMapping("/posts")
    public String index(Model viewModel) {
//        List<User> users = userDao.findAll();
//        List<Post> posts = postDao.findAll();
//        Post currentPost = new Post();
//
//
//        for (User currentUser : users) {
//            posts.add( userDao.findByUsername(currentUser.getUsername()).getPosts().get(0));
//        }
//
//        viewModel.addAttribute("users", users);
        List<Post> posts = postDao.findAll();
        for (Post currentPost : posts) {
            currentPost.getCategories();
        }
//        viewModel.addAttribute("user", posts.);
        viewModel.addAttribute("posts", posts);
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String show(@PathVariable long id, Model viewModel) {
        System.out.println("id = " + id);
        viewModel.addAttribute("post", postDao.getOne(id));
        viewModel.addAttribute("postImg", postDao.getOne(id).getPicture_url());
        viewModel.addAttribute("categories", postDao.getOne(id).getCategories());
        return "posts/show";
    }
    @GetMapping("/changeImage")
    public String changeBlogPicture(Model vModel) {
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        vModel.addAttribute("blogImage", loggedUser.getBlog_image());
        vModel.addAttribute("userId", loggedUser.getId());
        return "/posts/change-blog-image";
    }

    @PostMapping("/changeBlogImage/{id}")
    public String updateBlogPicture(@PathVariable long id,
                                    @RequestParam(name = "photoURL",
                                            required = false) String photoURL) {
        User loggedUser = userDao.getOne(id);
        loggedUser.setBlog_image(photoURL);
        userDao.save(loggedUser);
        return "redirect:/changeImage";
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
        int index;
        viewModel.addAttribute("picURL", postDao.getOne(id).getPicture_url());
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

    @PostMapping("add-photo/{id}")
    public String addPhoto(@PathVariable long id,
                           @RequestParam(name = "photoURL",
            required = false) String photoURL){
        Post post = postDao.getOne(id);
//        System.out.println("photoURL = " + photoURL);
        post.setPicture_url(photoURL);
        postDao.save(post);
        return "redirect:/posts/" + post.getId() + "/update";

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

    @PostMapping("/posts/{id}/delete")
    public String delete(@PathVariable long id) {
        postDao.deleteById(id);
        return "redirect:/myPosts";
    }


}
