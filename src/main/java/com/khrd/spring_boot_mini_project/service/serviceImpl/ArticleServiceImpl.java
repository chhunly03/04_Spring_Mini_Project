package com.khrd.spring_boot_mini_project.service.serviceImpl;

import com.khrd.spring_boot_mini_project.exception.BadRequestException;
import com.khrd.spring_boot_mini_project.model.entity.*;
import com.khrd.spring_boot_mini_project.model.request.articleRequest.ArticleRequest;
import com.khrd.spring_boot_mini_project.model.response.ApiResponce;
import com.khrd.spring_boot_mini_project.model.response.ArticleResponse;
import com.khrd.spring_boot_mini_project.model.response.CommentResponse;
import com.khrd.spring_boot_mini_project.model.response.articleResponseDTO.DTOResponseArticle;
import com.khrd.spring_boot_mini_project.model.response.userResponseDTO.UserResponseDTO;
import com.khrd.spring_boot_mini_project.repository.ArticleRepository;
import com.khrd.spring_boot_mini_project.repository.CategoryRepository;
import com.khrd.spring_boot_mini_project.repository.UserRepository;
import com.khrd.spring_boot_mini_project.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        Article article = Article.builder()
                .title(articleRequest.getTitle())
                .description(articleRequest.getDescription())
                .createAt(LocalDateTime.now())
                .user(user)
                .build();

        List<CategoryArticle> categoryArticles = new ArrayList<CategoryArticle>();
        for (Integer categoryId : articleRequest.getCategoryId()) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("category not found"));
            CategoryArticle categoryArticle = new CategoryArticle();
            categoryArticle.setArticle(article);
            categoryArticle.setCategory(category);
            categoryArticles.add(categoryArticle);

        }



        categoryArticles.forEach(categoryArticle -> categoryArticle.setArticle(article));
        article.setCategoryArticles(categoryArticles);
        return articleRepository.save(article).dtoResponse(userId);

    }

    @Override
    public List<ArticleResponse> getAllArticle(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase
                (Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Article> articlePage = articleRepository.findAll(pageable);

        List<ArticleResponse> articleResponses = articlePage.stream().map(article -> {
            List<Integer> categoryId = article.getCategoryArticles().stream()
                    .map(categoryArticle -> categoryArticle.getCategory()
                            .getCategoryId()).collect(Collectors.toList());

            List<CommentResponse> commentResponses = article.getComments().isEmpty() ? null : article.getComments().stream().map(comment -> {
                User user = comment.getUser();
                return new CommentResponse(
                        comment.getId(),
                        comment.getCmt(),
                        comment.getCreateAt(),
                        new UserResponseDTO(
                                user.getUserId(),
                                user.getUsername(),
                                user.getEmail(),
                                user.getAddress(),
                                user.getPhoneNumber(),
                                user.getCreateAt(),
                                user.getUpdateAt(),
                                user.getRole()
                        )
                );
            }).collect(Collectors.toList());
            return new ArticleResponse(
                    article.getId(),
                    article.getTitle(),
                    article.getDescription(),
                    article.getCreateAt(),
                    article.getUser().getUserId(),
                    categoryId,
                    commentResponses
            );
        }).collect(Collectors.toList());
        return articleResponses;

    }

    @Override
    public ArticleResponse getArticleById(Integer id) {
        return null;
    }


}
