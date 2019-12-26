package com.codeup.blog.blog.controllers;

import com.codeup.blog.blog.models.Category;
import com.codeup.blog.blog.models.Post;
import com.codeup.blog.blog.models.User;
import com.codeup.blog.blog.repositories.CategoryRepository;

import com.codeup.blog.blog.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoryController {

    private final CategoryRepository categoryDao;
    private final UserRepository userDao;

    public CategoryController(CategoryRepository categoryDao, UserRepository userDao) {
        this.categoryDao = categoryDao;
        this.userDao = userDao;
    }


    @GetMapping( "/category/add")
    public String showCreate(Model vModel) {
        vModel.addAttribute("category", new Category());
        return "/category/add";
    }


    @PostMapping("/category/add")
    public String create(@ModelAttribute Category newCategory) {
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newCategory.setUser(loggedUser);
        categoryDao.save(newCategory);
        return "redirect:/category/maintain";
    }

    @GetMapping("category/maintain")
    public String showCategory(Model viewModel) {
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        viewModel.addAttribute("categories", userDao.findByUsername(loggedUser.getUsername()).getCategories());
        return "/category/maintain";
    }

    @GetMapping( "/category/{id}/edit")
    public String updatePost(@PathVariable long id, Model viewModel) {
        viewModel.addAttribute("category", categoryDao.getOne(id));
        return "category/edit";
    }

    @PostMapping("/category/{id}/edit")
    public String update( @PathVariable long id,  @RequestParam String description) {
        Category oldCategory = categoryDao.getOne(id);
        oldCategory.setDescription(description);
        categoryDao.save(oldCategory);
        return "redirect:/category/maintain";
    }

    @PostMapping("/category/{id}/delete")
    public String updateCategory(@PathVariable long id) {

        categoryDao.deleteById(id);
        return "redirect:/category/maintain";
    }

    @Override
    public String toString() {
        return "CategoryController{" +
                "categoryDao=" + categoryDao +
                '}';
    }
}
