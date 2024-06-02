package com.akamai.homework.controllers;

import com.akamai.homework.dao.dto.PostDto;
import com.akamai.homework.services.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@Slf4j
public class PostController {
    PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/addPost")
    public ResponseEntity<String> addPost(@RequestParam(name = "userId") String userId, @RequestParam(name = "postTitle") String postTitle,
                          @RequestParam(name = "postText") String postText) {
        try {
            String responseJson = new ObjectMapper().writeValueAsString(postService.addPost(userId, postTitle, postText));
            return ResponseEntity.ok(responseJson);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/editPost")
    public ResponseEntity<String> editPostText(@RequestParam(name = "postId") String id,
                               @RequestParam(name = "newText") String newText) {
        try {
            String responseJson = new ObjectMapper().writeValueAsString(postService.editPostText(id, newText));
            return ResponseEntity.ok(responseJson);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Transactional
    @PostMapping("/upvote")
    public ResponseEntity<String> upvotingPost(@RequestParam(name = "id") String id, @RequestParam(name = "upvoted_by") String userId) {
        try {
            String responseJson = new ObjectMapper().writeValueAsString(postService.upvotingPost(id, userId));
            return ResponseEntity.ok(responseJson);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Transactional
    @PostMapping("/downvote")
    public ResponseEntity<String> downvotingPost(@RequestParam(name = "id") String id, @RequestParam(name = "downvote_by") String userId) {
        try {
            String responseJson = new ObjectMapper().writeValueAsString(postService.downvotingPost(id, userId));
            return ResponseEntity.ok(responseJson);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getPostsSortedAndByPaging")
    public ResponseEntity<Page<PostDto>> getPostsSortedAndByPaging(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                   @RequestParam(name = "size", defaultValue = "10") int size,
                                                                   @RequestParam(defaultValue = "asc") String sortDirection) {
        try {
            Sort sort = Sort.by(sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "points")
                    .and(Sort.by(Sort.Direction.DESC, "createdOn"));

            Pageable pageable = PageRequest.of(page, size, sort);
            return ResponseEntity.ok(postService.getPostsSortedAndByPaging(pageable));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
