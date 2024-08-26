package com.khrd.spring_boot_mini_project.service;

import com.khrd.spring_boot_mini_project.model.request.articleRequest.ArticleRequest;
import com.khrd.spring_boot_mini_project.model.request.commentRequest.CommentRequest;
import com.khrd.spring_boot_mini_project.model.response.ArticleResponse;
import com.khrd.spring_boot_mini_project.model.response.articleResponseDTO.DTOResponseArticle;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ArticleService {
    DTOResponseArticle createArticle(ArticleRequest articleRequest);
    List<ArticleResponse>getAllArticle (int page,int size,String sortBy,String direction);
    ArticleResponse getArticleById(Integer id);
    ArticleResponse createComment(Integer id, CommentRequest commentRequest);
    ArticleResponse getCommentById(Integer id);
    void deleteArticleById (Integer id);
    ArticleResponse updateArticleById(Integer id, ArticleRequest articleRequest);

}
