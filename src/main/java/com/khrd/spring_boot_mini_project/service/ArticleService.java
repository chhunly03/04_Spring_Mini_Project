package com.khrd.spring_boot_mini_project.service;

import com.khrd.spring_boot_mini_project.model.request.articleRequest.ArticleRequest;
import com.khrd.spring_boot_mini_project.model.response.articleResponseDTO.DTOResponseArticle;

public interface ArticleService {
    DTOResponseArticle createArticle(ArticleRequest articleRequest);
}
