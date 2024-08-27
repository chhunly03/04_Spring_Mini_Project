package com.khrd.spring_boot_mini_project.service.serviceImpl;

import com.khrd.spring_boot_mini_project.exception.NotFoundException;
import com.khrd.spring_boot_mini_project.model.response.comment.CommentCreateDTO;
import com.khrd.spring_boot_mini_project.model.userDetail.CustomUserDetails;
import com.khrd.spring_boot_mini_project.repository.ArticleRepository;
import com.khrd.spring_boot_mini_project.repository.UserRepository;
import com.khrd.spring_boot_mini_project.repository.entity.Article;
import com.khrd.spring_boot_mini_project.repository.entity.Comment;
import com.khrd.spring_boot_mini_project.model.request.comment.CommentRequest;
import com.khrd.spring_boot_mini_project.model.response.comment.CommentDTO;
import com.khrd.spring_boot_mini_project.repository.CommentRepository;
import com.khrd.spring_boot_mini_project.repository.entity.User;
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
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Override
    public CommentDTO getCommentById(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Integer getUser = customUserDetails.getUserId();
        if (commentRepository.findCommentIdByUserId(id, getUser) != null) {
            return commentRepository.findById(id).get().toResponse();
        }
        throw new NotFoundException("Can't find comment with id " + id);
    }

    @Override
    public CommentDTO updateCommentById(Integer id, CommentRequest x) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Integer getUser = customUserDetails.getUserId();
        if (commentRepository.findCommentIdByUserId(id, getUser) != null) {
            Comment comment = commentRepository.findById(id).get();
            comment.setCmt(x.getComment());
            comment.setUpdateAt(LocalDateTime.now());
            return commentRepository.save(comment).toResponse();
        }
        throw new NotFoundException("Can't find comment with id " + id);
    }

    @Override
    public void deleteCommentById(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Integer getUser = customUserDetails.getUserId();
        if (commentRepository.findCommentIdByUserId(id, getUser) != null) {
            commentRepository.deleteById(id);
            return;
        }
        throw new NotFoundException("Can't find comment with id " + id);
    }

    @Override
    public CommentCreateDTO createCommentArticle(Integer id, CommentRequest x) {
        if (articleRepository.findById(id).isPresent()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            Integer getUser = customUserDetails.getUserId();
            User getUserId = userRepository.findById(Math.toIntExact(getUser)).get();

            Article article = articleRepository.findById(id).get();
            Comment comment = commentRepository.save(x.toEntity());
            comment.setArticle(article);
            comment.setUser(getUserId);

            return commentRepository.save(comment).toResponseCreate();
        }
        throw new NotFoundException("Can't find article with id " + id);
    }

    @Override
    public CommentCreateDTO getCommentByArticleId(Integer id) {
        if (articleRepository.findById(id).isPresent()) {
            return articleRepository.findById(id).get().toResponseCreateComment();
        }
        throw new NotFoundException("Can't find article with id " + id);
    }
}
