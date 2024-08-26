package com.khrd.spring_boot_mini_project.service;

import com.khrd.spring_boot_mini_project.model.request.comment.CommentRequest;
import com.khrd.spring_boot_mini_project.model.response.comment.CommentCreateDTO;
import com.khrd.spring_boot_mini_project.model.response.comment.CommentDTO;

public interface CommentService {
    CommentDTO getCommentById(Integer id);

    CommentDTO updateCommentById(Integer id, CommentRequest x);

    void deleteCommentById(Integer id);

    CommentCreateDTO createCommentArticle(Integer id, CommentRequest x);

    CommentCreateDTO getCommentByArticleId(Integer id);
}
