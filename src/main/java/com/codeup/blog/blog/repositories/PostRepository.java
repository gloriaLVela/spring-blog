package com.codeup.blog.blog.repositories;

import com.codeup.blog.blog.models.Post;
import com.codeup.blog.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByTitle(String title);
}


