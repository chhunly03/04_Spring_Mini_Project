package com.khrd.spring_boot_mini_project.service.serviceImpl;
import com.khrd.spring_boot_mini_project.exception.ForbiddenException;
import com.khrd.spring_boot_mini_project.exception.NotFoundException;
import com.khrd.spring_boot_mini_project.model.entity.*;
import com.khrd.spring_boot_mini_project.model.request.articleRequest.ArticleRequest;
import com.khrd.spring_boot_mini_project.model.request.commentRequest.CommentRequest;
import com.khrd.spring_boot_mini_project.model.response.ArticleResponse;
import com.khrd.spring_boot_mini_project.model.response.CommentResponse;
import com.khrd.spring_boot_mini_project.model.response.articleResponseDTO.DTOResponseArticle;
import com.khrd.spring_boot_mini_project.model.response.userResponseDTO.UserResponseDTO;
import com.khrd.spring_boot_mini_project.repository.ArticleRepository;
import com.khrd.spring_boot_mini_project.repository.CategoryArticleRepository;
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
    private final CategoryArticleRepository categoryArticleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, CategoryRepository categoryRepository, UserRepository userRepository, CategoryArticleRepository categoryArticleRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.categoryArticleRepository = categoryArticleRepository;
    }

    @Override
    public DTOResponseArticle createArticle(ArticleRequest articleRequest) throws ForbiddenException {
        Integer userId = GetCurrentUser.userId();
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user not found"));
        if (user.getRole().equalsIgnoreCase("READER")) {
            throw new ForbiddenException("Yor are not allowed to add articles");
        }
        Article article = Article.builder()
                .title(articleRequest.getTitle())
                .description(articleRequest.getDescription())
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .user(user)
                .build();

        List<CategoryArticle> categoryArticles = new ArrayList<CategoryArticle>();
        for (Integer categoryId : articleRequest.getCategoryId()) {
            List<Category> category = categoryRepository.findCategoriesByUserUserId(userId);
            Category categoryList = category.stream().filter(c -> c.getCategoryId().equals(categoryId)).findFirst().orElseThrow(() -> new NotFoundException("Category not found with id " + categoryId));
            CategoryArticle categoryArticle = new CategoryArticle();
            categoryArticle.setArticle(article);
            categoryArticle.setCategory(categoryList);
            categoryArticle.setCreateAt(LocalDateTime.now());
            categoryArticle.setUpdateAt(LocalDateTime.now());
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
                                user.getCreatedAt(),
                                user.getUpdatedAt(),
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
                .orElseThrow(() -> new NotFoundException("Article not found"));
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
                                    user.getCreatedAt(),
                                    user.getUpdatedAt(),
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
                .orElseThrow(() -> new NotFoundException("Article not found"));
        Integer userId = GetCurrentUser.userId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
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
                            cmtUser.getCreatedAt(),
                            cmtUser.getUpdatedAt(),
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
                .orElseThrow(() -> new NotFoundException("Article not found"));

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
                            user.getCreatedAt(),
                            user.getUpdatedAt(),
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
    public void deleteArticleById(Integer id) throws NotFoundException {
        Integer userId = GetCurrentUser.userId();
        checkRole(userId, "You cannot delete articles");
        ArticleResponse articleResponse =  getArticleById(id);
        if (!articleResponse.getOwnerOfArticle().equals(userId)) {
            throw new NotFoundException("You cannot delete articles not found with id "+ id);
        }
        getArticleById(id);
        articleRepository.deleteById(id);

    }
    public void checkRole(Integer id ,String message) {
        User user = userRepository.findById(id).orElseThrow(()->new NotFoundException("User not found"));
        if (user.getRole().equalsIgnoreCase("READER")){
            throw new ForbiddenException(message);
        }
    }

    @Override
    public ArticleResponse updateArticleById(Integer id, ArticleRequest articleRequest) {
        Integer userId = GetCurrentUser.userId();
        checkRole(userId, "You cannot delete articles");
        ArticleResponse articleResponse =  getArticleById(id);
        if (!articleResponse.getOwnerOfArticle().equals(userId)) {
            throw new NotFoundException("You cannot delete articles not found with id "+ id);
        }
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Article not found"));
        categoryArticleRepository.deleteByArticleId(id);
        article.setId(id);
        article.setTitle(articleRequest.getTitle());
        article.setDescription(articleRequest.getDescription());

        List<CategoryArticle> updatedCategoryArticles = new ArrayList<>();
        for (Integer categoryId : articleRequest.getCategoryId()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new NotFoundException("Category not found"));
            CategoryArticle categoryArticle = new CategoryArticle();
            categoryArticle.setArticle(article);
            categoryArticle.setCategory(category);
            updatedCategoryArticles.add(categoryArticle);
        }

        article.setCategoryArticles(updatedCategoryArticles);


        Article updatedArticle = articleRepository.save(article);

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
                                    user.getCreatedAt(),
                                    user.getUpdatedAt(),
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
