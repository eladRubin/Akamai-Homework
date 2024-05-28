package com.akamai.homework.services;

import com.akamai.homework.dao.entities.Post;
import com.akamai.homework.dao.entities.User;
import com.akamai.homework.dao.repositories.PostRepository;
import com.akamai.homework.dao.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostService {
    PostRepository postRepository;
    UserRepository userRepository;
    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public String addPost(String userId, String postTitle, String postText) {
        log.info("adding new post to the system: {} for user id: {}", postTitle, userId);
        try {
            Post newPost = new Post(userId, postTitle, postText);
            User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(() ->
                    new Exception("not found user with id = " + userId)); //TODO: add logic
            newPost.setUser(user);
            postRepository.saveAndFlush(newPost);
        } catch (Exception e) {
            log.error("could not add new post: {}", e.toString());
        }
        return "Saved";
    }

    public String editPost(String id, String newText) {
        log.info("edit post's text: {}", newText);
        try {
            postRepository.updateTextById(Long.parseLong(id), newText);
            postRepository.flush();
        } catch (Exception e) {
            log.error("could not update post: {}", e.toString());
        }

        return "Saved";
    }

    public String upvotingPost (String id, String userId) {
        log.info("upvoting a post: {} by user: {}", id, userId); //TODO: change to userName from Request

        try {
            int currentPoints = postRepository.selectPointsById(Long.parseLong(id));
            log.info("current points: {}", currentPoints);
            currentPoints++;
            postRepository.updatePointsById(Long.parseLong(id), currentPoints);
            postRepository.flush();
        } catch (Exception e) {
            log.error("could not update points: {}", e.toString());
        }

        return "Saved";
    }

    public String downvotingPost(String id, String userId) {
        log.info("downvotingPost a post: {} by user: {}", id, userId);

        try {
            int currentPoints = postRepository.selectPointsById(Long.parseLong(id));
            log.info("current points: {}", currentPoints);
            if (currentPoints > 0)  {
                currentPoints--;
                postRepository.updatePointsById(Long.parseLong(id), currentPoints);
                postRepository.flush();
            }
        } catch (Exception e) {
            log.error("could not update points: {}", e.toString());
        }

        return "Saved";
    }

    public List<String> getAllTopics() {
        return postRepository.findAll().stream()
        .map(Post::getTitle)
        .distinct()
        .collect(Collectors.toList());
    }

    public String getTextById(String id) {
        try {
            return postRepository.selectTextById(Long.parseLong(id));
        } catch (Exception e) {
            log.error("could not get text for post: {}, {}", id, e.toString());
        }

        return null;
    }
}
