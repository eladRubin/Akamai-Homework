package com.akamai.homework.dao.repositories;

import com.akamai.homework.dao.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Transactional
    @Modifying
    @Query("update Post p set p.text = ?2 where p.id = ?1")
    void updateTextById(Long id, String text) throws Exception;

    @Transactional
    @Modifying
    @Query("update Post p set p.points = ?2 where p.id = ?1")
    void updatePointsById(Long id, int points) throws Exception;

    @Query("select p.points from Post p where p.id = ?1")
    int selectPointsById(Long id);

    @Query("select p.text from Post p where p.id = ?1")
    String selectTextById(Long id);
}
