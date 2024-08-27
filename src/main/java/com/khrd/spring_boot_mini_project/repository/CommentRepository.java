package com.khrd.spring_boot_mini_project.repository;

import com.khrd.spring_boot_mini_project.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment , Integer> {
    @Query("SELECT b FROM Comment b WHERE b.id = :commentId AND b.user.userId = :userId")
    Comment findCommentByUserId(Integer userId, Integer commentId);
}
