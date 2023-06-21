package com.example.uhavrend.models;

import lombok.Data;

import java.util.List;

@Data
public class AddPostRequest {
    private Integer userId;
    private Integer postIds;
}
