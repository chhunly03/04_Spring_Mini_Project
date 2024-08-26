package com.khrd.spring_boot_mini_project.service.serviceImpl;

import com.khrd.spring_boot_mini_project.exception.NotFoundException;
import com.khrd.spring_boot_mini_project.model.entity.Comment;
import com.khrd.spring_boot_mini_project.model.request.comment.CommentRequest;
import com.khrd.spring_boot_mini_project.model.response.comment.CommentDTO;
import com.khrd.spring_boot_mini_project.repository.CommentRepository;
import com.khrd.spring_boot_mini_project.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public CommentDTO getCommentById(Integer id) {
        if (commentRepository.findById(id).isPresent()) {
            return commentRepository.findById(id).get().toResponse();
        }
        throw new NotFoundException("Can't find comment with id " + id);
    }

    @Override
    public CommentDTO updateCommentById(Integer id, CommentRequest x) {
        if (commentRepository.findById(id).isPresent()) {
            Comment comment = commentRepository.findById(id).get();
            comment.setCmt(x.getComment());
            comment.setUpdateAt(LocalDateTime.now());
            return commentRepository.save(comment).toResponse();
        }
        throw new NotFoundException("Can't find comment with id " + id);
    }

    @Override
    public void deleteCommentById(Integer id) {
        if (commentRepository.findById(id).isPresent()) {
            commentRepository.deleteById(id);
        }
        throw new NotFoundException("Can't find comment with id " + id);
    }
}
