package com.khrd.spring_boot_mini_project.repository;

import com.khrd.spring_boot_mini_project.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment , Integer> {
}
