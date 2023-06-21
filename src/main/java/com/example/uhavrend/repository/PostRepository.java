package com.example.uhavrend.repository;

import com.example.uhavrend.entity.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
