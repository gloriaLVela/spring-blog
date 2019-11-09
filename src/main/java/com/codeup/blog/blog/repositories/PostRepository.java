package com.codeup.blog.blog.Repositories;

import com.codeup.blog.blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}


