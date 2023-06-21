package com.example.uhavrend.entity;

import com.example.uhavrend.models.PostsRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String text;
    private Date date;

    public Post(PostsRequest postsRequest) {
        this.text = postsRequest.getText();
        this.date = postsRequest.getDate();
    }

}
