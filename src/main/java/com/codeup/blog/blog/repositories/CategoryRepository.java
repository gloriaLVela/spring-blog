package com.codeup.blog.blog.repositories;

import com.codeup.blog.blog.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository  extends JpaRepository<Category, Long> {
}
