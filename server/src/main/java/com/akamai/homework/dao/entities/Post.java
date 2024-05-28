package com.akamai.homework.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Data
@RequiredArgsConstructor
@Entity
@Table(name="POSTS")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    int points = 0;

    @Column
    private Date createdOn = new Date();

    @NonNull
    @Column
    private String createdBy;

    @NonNull
    @Column
    private String title;

    @NonNull
    @Column

    private String text;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @JsonIgnore
    private User user;

    public Post() {

    }
}
