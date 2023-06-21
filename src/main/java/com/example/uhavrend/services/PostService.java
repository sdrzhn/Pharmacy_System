package com.example.uhavrend.services;

import com.example.uhavrend.entity.Post;
import com.example.uhavrend.models.PostsRequest;
import com.example.uhavrend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public Post create(PostsRequest request) {
        request.setDate(new Date());
        return postRepository.save(new Post(request));
    }
}
