package com.khrd.spring_boot_mini_project.repository;

import com.khrd.spring_boot_mini_project.repository.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("SELECT b FROM Comment b WHERE b.id = :cmtId AND b.user.userId = :userId")
    Comment findCommentIdByUserId(Integer userId, Integer cmtId);
}
