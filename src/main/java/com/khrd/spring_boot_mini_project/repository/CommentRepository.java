package com.khrd.spring_boot_mini_project.repository;

import com.khrd.spring_boot_mini_project.repository.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
