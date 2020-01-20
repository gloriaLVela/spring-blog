package com.codeup.blog.blog.repositories;

import com.codeup.blog.blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByTitle(String title);
}




