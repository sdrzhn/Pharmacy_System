package com.example.uhavrend.controllers;

import com.example.uhavrend.entity.Post;
import com.example.uhavrend.models.PostsRequest;
import com.example.uhavrend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping
    public Post create(@RequestBody PostsRequest post) {
        return postService.create(post);
    }
}
