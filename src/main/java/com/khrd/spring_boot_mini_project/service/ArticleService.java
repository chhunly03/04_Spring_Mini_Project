package com.khrd.spring_boot_mini_project.service;

import com.khrd.spring_boot_mini_project.model.request.articleRequest.ArticleRequest;
import com.khrd.spring_boot_mini_project.model.response.ArticleResponse;
import com.khrd.spring_boot_mini_project.model.response.articleResponseDTO.DTOResponseArticle;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ArticleService {
    DTOResponseArticle createArticle(ArticleRequest articleRequest);
    List<ArticleResponse>getAllArticle (int page,int size,String sortBy,String direction);
    ArticleResponse getArticleById(Integer id);
}
