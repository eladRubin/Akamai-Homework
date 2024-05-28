package com.akamai.homework.controllers;

import com.akamai.homework.dao.entities.User;
import com.akamai.homework.services.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class PostController {
    PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PostMapping("/addPost")
    public String addPost(@RequestParam(name = "userId") String userId, @RequestParam(name = "postTitle") String postTitle,
                          @RequestParam(name = "postText") String postText) {
        return postService.addPost(userId, postTitle, postText);
    }

    @PostMapping("/editPost")
    public String editPostText(@RequestParam(name = "id") String id,
                               @RequestParam(name = "newText") String newText) {
        return postService.editPost(id, newText);
    }

    @PostMapping("/upvote")
    public String upvotingPost(@RequestParam(name = "id") String id, @RequestParam(name = "upvoted_by") String userId) {
        return postService.upvotingPost(id, userId);
    }

    @PostMapping("/downvote")
    public String downvotingPost(@RequestParam(name = "id") String id, @RequestParam(name = "downvote_by") String userId) {
        return postService.downvotingPost(id, userId);
    }

    @GetMapping("/getAllTopics")
    public List<String> getAllTopics() {
        return postService.getAllTopics();
    }

    @GetMapping("/getText")
    public String getTextById(@RequestParam(name = "id") String id) {
        return postService.getTextById(id);
    }
}
