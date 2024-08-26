package com.khrd.spring_boot_mini_project.service.serviceImpl;

import com.khrd.spring_boot_mini_project.exception.BadRequestException;
import com.khrd.spring_boot_mini_project.model.request.articleRequest.ArticleRequest;
import com.khrd.spring_boot_mini_project.model.response.articleResponseDTO.DTOResponseArticle;
import com.khrd.spring_boot_mini_project.repository.ArticleRepository;
import com.khrd.spring_boot_mini_project.repository.CategoryRepository;
import com.khrd.spring_boot_mini_project.repository.UserRepository;
import com.khrd.spring_boot_mini_project.repository.entity.*;
import com.khrd.spring_boot_mini_project.service.ArticleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DTOResponseArticle createArticle(ArticleRequest articleRequest) {
        Integer userId = GetCurrentUser.userId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        if (user.getRole().equalsIgnoreCase("READER")) {

            throw new BadRequestException("Yor are not allowed to add articles");

        }
        List<CategoryArticle> categoryArticles = new ArrayList<CategoryArticle>();
        for (Integer categoryId : articleRequest.getCategoryId()) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("category not found"));
            CategoryArticle categoryArticle = new CategoryArticle();
            categoryArticle.setCategory(category);
            categoryArticles.add(categoryArticle);
        }
        Article article = Article.builder()
                .title(articleRequest.getTitle())
                .description(articleRequest.getDescription())
                .createAt(LocalDateTime.now())
                .build();
        categoryArticles.forEach(categoryArticle -> categoryArticle.setArticle(article));
        return articleRepository.save(article).dtoResponse(userId);

    }
}
