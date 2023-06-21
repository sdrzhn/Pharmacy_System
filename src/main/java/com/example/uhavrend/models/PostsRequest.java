package com.example.uhavrend.models;

import lombok.Data;

import java.util.Date;

@Data
public class PostsRequest {
    private int id;
    private Date date;
    private String text;
    private String email;
}
