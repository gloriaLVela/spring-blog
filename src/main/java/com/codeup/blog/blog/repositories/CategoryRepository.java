package com.codeup.blog.blog.repositories;

import com.codeup.blog.blog.models.Category;
import com.codeup.blog.blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository  extends JpaRepository<Category, Long> {

}


