package com.akamai.homework.controllers;

import com.akamai.homework.dao.dto.PostDto;
import com.akamai.homework.services.PostService;
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
            return ResponseEntity.ok(postService.addPost(userId, postTitle, postText));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/editPost")
    public ResponseEntity<String> editPostText(@RequestParam(name = "id") String id,
                               @RequestParam(name = "newText") String newText) {
        try {
            return  ResponseEntity.ok(postService.editPostText(id, newText));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Transactional
    @PostMapping("/upvote")
    public ResponseEntity<String> upvotingPost(@RequestParam(name = "id") String id, @RequestParam(name = "upvoted_by") String userId) {
        try {
            return ResponseEntity.ok(postService.upvotingPost(id, userId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Transactional
    @PostMapping("/downvote")
    public ResponseEntity<String> downvotingPost(@RequestParam(name = "id") String id, @RequestParam(name = "downvote_by") String userId) {
        try {
            return ResponseEntity.ok(postService.downvotingPost(id, userId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//TODO: remove if not needed

//    @GetMapping("/getAllTopics")
//    public List<String> getAllTopics() {
//        return postService.getAllTopics();
//    }
//
//    @GetMapping("/getText")
//    public String getTextById(@RequestParam(name = "id") String id) {
//        return postService.getTextById(id);
//    }

    @GetMapping("/getPostsSortedAndByPaging")
    public ResponseEntity<Page<PostDto>> getPostsSortedAndByPaging(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                   @RequestParam(name = "size", defaultValue = "10") int size,
                                                                   @RequestParam(defaultValue = "asc") String sortDirection) {
        try {
            Sort sort = Sort.by(sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "createdOn")
                    .and(Sort.by(sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "points"));

            Pageable pageable = PageRequest.of(page, size, sort);
            return ResponseEntity.ok(postService.getPostsSortedAndByPaging(pageable));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
