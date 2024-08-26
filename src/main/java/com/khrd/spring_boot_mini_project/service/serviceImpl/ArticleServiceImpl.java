package com.khrd.spring_boot_mini_project.service.serviceImpl;

import com.khrd.spring_boot_mini_project.exception.BadRequestException;
import com.khrd.spring_boot_mini_project.model.entity.*;
import com.khrd.spring_boot_mini_project.model.request.articleRequest.ArticleRequest;
import com.khrd.spring_boot_mini_project.model.request.commentRequest.CommentRequest;
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
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        List<Integer> categoryIds = article.getCategoryArticles().stream()
                .map(categoryArticle -> categoryArticle.getCategory().getCategoryId())
                .collect(Collectors.toList());

        List<CommentResponse> commentResponses = article.getComments().isEmpty() ? null :
                article.getComments().stream().map(comment -> {
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
                categoryIds,
                commentResponses
        );
    }

    @Override
    public ArticleResponse createComment(Integer id, CommentRequest commentRequest) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        Integer userId = GetCurrentUser.userId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Comment comment = Comment.builder()
                .cmt(commentRequest.getComment())
                .createAt(LocalDateTime.now())
                .article(article)
                .user(user)
                .build();
        article.getComments().add(comment);

        articleRepository.save(article);

        List<Integer> categoryIds = article.getCategoryArticles().stream()
                .map(categoryArticle -> categoryArticle.getCategory().getCategoryId())
                .collect(Collectors.toList());

        List<CommentResponse> commentResponses = article.getComments().stream().map(cmt -> {
            User cmtUser = cmt.getUser();
            return new CommentResponse(
                    cmt.getId(),
                    cmt.getCmt(),
                    cmt.getCreateAt(),
                    new UserResponseDTO(
                            cmtUser.getUserId(),
                            cmtUser.getUsername(),
                            cmtUser.getEmail(),
                            cmtUser.getAddress(),
                            cmtUser.getPhoneNumber(),
                            cmtUser.getCreateAt(),
                            cmtUser.getUpdateAt(),
                            cmtUser.getRole()
                    )
            );
        }).collect(Collectors.toList());

        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getDescription(),
                article.getCreateAt(),
                article.getUser().getUserId(),
                categoryIds,
                commentResponses
        );
    }

    @Override
    public ArticleResponse getCommentById(Integer id) {
        // Find the article by its ID
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        List<Integer> categoryIds = article.getCategoryArticles().stream()
                .map(categoryArticle -> categoryArticle.getCategory().getCategoryId())
                .collect(Collectors.toList());

        List<CommentResponse> commentResponses = article.getComments().stream().map(comment -> {
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
                categoryIds,
                commentResponses
        );
    }

    @Override
    public void deleteArticleById(Integer id) {
        articleRepository.deleteById(id);

    }

    @Override
    public ArticleResponse updateArticleById(Integer id, ArticleRequest articleRequest) {
        // Retrieve the article from the database
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        // Update the article with new data
        article.setTitle(articleRequest.getTitle());
        article.setDescription(articleRequest.getDescription());
        // Update other fields as needed

        // Update categories
        List<CategoryArticle> updatedCategoryArticles = new ArrayList<>();
        for (Integer categoryId : articleRequest.getCategoryId()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            CategoryArticle categoryArticle = new CategoryArticle();
            categoryArticle.setArticle(article);
            categoryArticle.setCategory(category);
            updatedCategoryArticles.add(categoryArticle);
        }

        article.setCategoryArticles(updatedCategoryArticles);

        // Save the updated article
        Article updatedArticle = articleRepository.save(article);

        // Convert to response DTO
        List<Integer> categoryIds = updatedArticle.getCategoryArticles().stream()
                .map(categoryArticle -> categoryArticle.getCategory().getCategoryId())
                .collect(Collectors.toList());

        List<CommentResponse> commentResponses = updatedArticle.getComments().stream()
                .map(comment -> {
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
                updatedArticle.getId(),
                updatedArticle.getTitle(),
                updatedArticle.getDescription(),
                updatedArticle.getCreateAt(),
                updatedArticle.getUser().getUserId(),
                categoryIds,
                commentResponses
        );
    }



}
