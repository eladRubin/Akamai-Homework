package com.akamai.homework.dao.dto;

import com.akamai.homework.dao.entities.Post;
import lombok.Data;

@Data
public class PostDto {
    private long id;
    private int points;
    private String title;
    private String text;
    private String created_by;
    private String created_on;
    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.text = post.getText();
        this.points = post.getPoints();
        this.created_by = post.getCreatedBy();
        this.created_on = post.getCreatedOn();
    }
}
