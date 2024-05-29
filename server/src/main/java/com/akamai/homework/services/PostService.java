package com.akamai.homework.services;

import com.akamai.homework.dao.entities.Post;
import com.akamai.homework.dao.entities.User;
import com.akamai.homework.dao.repositories.PostRepository;
import com.akamai.homework.dao.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostService {
    PostRepository postRepository;
    UserRepository userRepository;

    final String UPDATED_POINTS_SUCCESSFULLY_MESSAGE = "updated points successfully";
    final String COULD_NOT_UPDATE_POINTS_SUCCESSFULLY_MESSAGE = "could not update points: ";
    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public String addPost(String userId, String postTitle, String postText) throws Exception {
        log.info("adding new post to the system: {} for user id: {}", postTitle, userId);
        try {
            Post newPost = new Post(userId, postTitle, postText);
            User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(() ->
                    new Exception("not found user with id = " + userId)); //TODO: add logic
            newPost.setUser(user);
            postRepository.saveAndFlush(newPost);
        } catch (Exception e) {
            throw new Exception("could not add new post: " + e);
        }
        return "new post added successfully";
    }

    public String editPostText(String id, String newText) throws Exception {
        log.info("edit post's: {} text: {}", id, newText);
        try {
            postRepository.updateTextById(Long.parseLong(id), newText);
            postRepository.flush();
        } catch (Exception e) {
            throw new Exception("could not update post: " + e);
        }

        return "post's text edit successfully";
    }

    //TODO: update user did that action in the updated by field
    public String upvotingPost (String id, String userId) throws Exception {
        log.info("upvoting a post: {} by user: {}", id, userId); //TODO: change to userName from Request

        try {
            // TODO: add new field in entity: upvotes
            int currentPoints = postRepository.selectPointsById(Long.parseLong(id));
            log.info("current points: {}", currentPoints);
            currentPoints++;
            postRepository.updatePointsById(Long.parseLong(id), currentPoints);
            postRepository.flush();
        } catch (Exception e) {
            throw new Exception(COULD_NOT_UPDATE_POINTS_SUCCESSFULLY_MESSAGE + e);
        }

        return UPDATED_POINTS_SUCCESSFULLY_MESSAGE;
    }

    //TODO: update user did that action in the updated by field
    public String downvotingPost(String id, String userId) throws Exception {
        log.info("downvotingPost a post: {} by user: {}", id, userId);

        try {
            int currentPoints = postRepository.selectPointsById(Long.parseLong(id));
            log.info("current points: {}", currentPoints);
            // TODO: add new field in entity: downvotes
            if (currentPoints > 0)  {
                currentPoints--;
                postRepository.updatePointsById(Long.parseLong(id), currentPoints);
                postRepository.flush();
            }
        } catch (Exception e) {
            throw new Exception(COULD_NOT_UPDATE_POINTS_SUCCESSFULLY_MESSAGE + e);
        }

        return UPDATED_POINTS_SUCCESSFULLY_MESSAGE;
    }
//TODO: remove if not needed!!!

//    public List<String> getAllTopics() {
//        return postRepository.findAll().stream()
//        .map(Post::getTitle)
//        .distinct()
//        .collect(Collectors.toList());
//    }
//
//    public String getTextById(String id) {
//        try {
//            return postRepository.selectTextById(Long.parseLong(id));
//        } catch (Exception e) {
//            log.error("could not get text for post: {}, {}", id, e.toString());
//        }
//
//        return null;
//    }

    public Page<Post> getPostsSortedAndByPaging(Pageable pageable) throws Exception {
        try {
            log.info("trying to receive posts by paging");
            return postRepository.findAll(pageable);
        } catch (Exception e) {
            throw new Exception("could not get posts: " + e);
        }
    }
}
