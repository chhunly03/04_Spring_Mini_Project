package com.khrd.spring_boot_mini_project.service.serviceImpl;

import com.khrd.spring_boot_mini_project.exception.NotFoundException;
import com.khrd.spring_boot_mini_project.model.entity.Comment;
import com.khrd.spring_boot_mini_project.model.request.commentRequest.CommentRequest;
import com.khrd.spring_boot_mini_project.model.response.comment.CommentDTO;
import com.khrd.spring_boot_mini_project.model.userDetail.CustomUserDetails;
import com.khrd.spring_boot_mini_project.repository.CommentRepository;
import com.khrd.spring_boot_mini_project.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public CommentDTO getCommentById(Integer id) {
        if (commentRepository.findById(id).isPresent()) {
            return commentRepository.findById(id).get().toResponseV1();
        }
        throw new NotFoundException("Can't find comment with id " + id);
    }

    @Override
    public CommentDTO updateCommentById(Integer id, CommentRequest x) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Integer getUserId = customUserDetails.getUserId();
        if (commentRepository.findCommentByUserId(getUserId, id) != null) {
            Comment comment = commentRepository.findById(id).get();
            comment.setCmt(x.getComment());
            comment.setUpdateAt(LocalDateTime.now());
            return commentRepository.save(comment).toResponseV1();
        }
        throw new NotFoundException("Can't find comment with id " + id);
    }

    @Override
    public void deleteCommentById(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Integer getUserId = customUserDetails.getUserId();
        if (commentRepository.findCommentByUserId(getUserId, id) != null) {
            commentRepository.deleteById(id);
        }
        throw new NotFoundException("Can't find comment with id " + id);
    }
}