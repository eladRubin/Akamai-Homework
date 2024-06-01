package com.akamai.homework.services;

import com.akamai.homework.dao.dto.PostDto;
import com.akamai.homework.dao.entities.Post;
import com.akamai.homework.dao.entities.User;
import com.akamai.homework.dao.repositories.PostRepository;
import com.akamai.homework.dao.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
            User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(() ->
                    new Exception("not found user with id = " + userId));
            Post newPost = new Post(user.getUserName(), postTitle, postText);
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

    public String upvotingPost (String id, String userId) throws Exception {
        log.info("upvoting a post: {} by user: {}", id, userId);

        try {
            int currentUpvotesPoints = postRepository.selectUpvotesPointsById(Long.parseLong(id));
            log.info("current upvotes points: {}", currentUpvotesPoints);
            currentUpvotesPoints++;
            postRepository.updateUpvotesPointsById(Long.parseLong(id), currentUpvotesPoints);
            postRepository.flush();
        } catch (Exception e) {
            throw new Exception(COULD_NOT_UPDATE_POINTS_SUCCESSFULLY_MESSAGE + e);
        }

        return UPDATED_POINTS_SUCCESSFULLY_MESSAGE;
    }

    public String downvotingPost(String id, String userId) throws Exception {
        log.info("downvotingPost a post: {} by user: {}", id, userId);

        try {
            int currentDownvotesPoints = postRepository.selectDownvotesPointsById(Long.parseLong(id));
            log.info("current downvotes points: {}", currentDownvotesPoints);
            currentDownvotesPoints++;
            postRepository.updateDownvotesPointsById(Long.parseLong(id), currentDownvotesPoints);
            postRepository.flush();
        } catch (Exception e) {
            throw new Exception(COULD_NOT_UPDATE_POINTS_SUCCESSFULLY_MESSAGE + e);
        }

        return UPDATED_POINTS_SUCCESSFULLY_MESSAGE;
    }

    public Page<PostDto> getPostsSortedAndByPaging(Pageable pageable) throws Exception {
        try {
            log.info("trying to receive posts by paging");
            Page<Post> posts = postRepository.findAll(pageable);

            return posts.map(PostDto::new);
        } catch (Exception e) {
            throw new Exception("could not get posts: " + e);
        }
    }
}
