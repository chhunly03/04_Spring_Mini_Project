package com.khrd.spring_boot_mini_project.service;

import com.khrd.spring_boot_mini_project.model.request.commentRequest.CommentRequest;
import com.khrd.spring_boot_mini_project.model.response.comment.CommentDTO;

public interface CommentService {
    CommentDTO getCommentById(Integer id);

    CommentDTO updateCommentById(Integer id, CommentRequest x);

    void deleteCommentById(Integer id);
}