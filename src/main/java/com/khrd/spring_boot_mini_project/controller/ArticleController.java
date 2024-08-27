package com.khrd.spring_boot_mini_project.controller;

import com.khrd.spring_boot_mini_project.exception.ForbiddenException;
import com.khrd.spring_boot_mini_project.model.request.articleRequest.ArticleRequest;
import com.khrd.spring_boot_mini_project.model.request.commentRequest.CommentRequest;
import com.khrd.spring_boot_mini_project.model.response.ApiResponse;
import com.khrd.spring_boot_mini_project.model.response.ArticleResponse;
import com.khrd.spring_boot_mini_project.model.response.articleResponseDTO.DTOResponseArticle;
import com.khrd.spring_boot_mini_project.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@AllArgsConstructor
@Data
@RequestMapping("/api/v1")
@SecurityRequirement(name = "bearerAuth")
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/author/article")
    @Operation(summary = "Create a new article")
    public ResponseEntity<ApiResponse<DTOResponseArticle>> createArticle(@RequestBody ArticleRequest articleRequest) throws ForbiddenException {
        DTOResponseArticle dtoResponseArticle =articleService.createArticle(articleRequest);
        ApiResponse<DTOResponseArticle> apiResponse = ApiResponse.<DTOResponseArticle>builder()
                .message("A new article is created successfully.")
                .status(HttpStatus.CREATED)
                .payload(dtoResponseArticle)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/article/all")
    @Operation(summary = "Get all available articles")
    public ResponseEntity<ApiResponse<List<ArticleResponse>>> getAllArticle(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction

    ){
        List<ArticleResponse> articleResponses =articleService.getAllArticle(page, size, sortBy, direction);
        ApiResponse<List<ArticleResponse>> apiResponse = ApiResponse.<List<ArticleResponse>>builder()
                .message("Get all articles successfully.")
                .status(HttpStatus.OK)
                .payload(articleResponses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/article/{id}")
    @Operation(summary = "Get article by id")
    public ResponseEntity<ApiResponse<ArticleResponse>> getArticleById(@Positive(message = "must be greater than 0 or must be positive") @PathVariable Integer id){
        ArticleResponse articleResponse = articleService.getArticleById(id);
        ApiResponse<ArticleResponse> apiResponse = ApiResponse.<ArticleResponse>builder()
                .message("Get article with id " +id+ " successfully. ")
                .status(HttpStatus.OK)
                .payload(articleResponse)
                .build();
        return ResponseEntity.ok(apiResponse);

    }
    @PostMapping("/article/{id}/comment")
    @Operation(summary = "Create a comment for a article")
    public ResponseEntity<ApiResponse<ArticleResponse>> createComment(@Positive(message = "must be greater than 0 or must be positive") @PathVariable Integer id, @RequestBody CommentRequest commentRequest){
        ArticleResponse articleResponse = articleService.createComment(id, commentRequest);
        ApiResponse<ArticleResponse> apiResponse = ApiResponse.<ArticleResponse>builder()
                .message("A new comment is posted on article" + id)
                .status(HttpStatus.OK)
                .payload(articleResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/article/{id}/comment")
    @Operation(summary = "Get comment on any article")
    public ResponseEntity<ApiResponse<ArticleResponse>> getComment(@Positive(message = "must be greater than 0 or must be positive") @PathVariable Integer id){
        ArticleResponse articleResponse = articleService.getCommentById(id);
        ApiResponse<ArticleResponse> apiResponse = ApiResponse.<ArticleResponse>builder()
                .message("Get all comments on article id " + id + " successfully")
                .status(HttpStatus.OK)
                .payload(articleResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Article by id")
    public ResponseEntity<ApiResponse<ArticleResponse>> deleteArticleById(@Positive(message = "must be greater than 0 or must be positive") @PathVariable Integer id) throws FontFormatException {
        articleService.deleteArticleById(id);
        ApiResponse <ArticleResponse>apiResponse = ApiResponse.<ArticleResponse>builder()
                .message("Delete a product by id " + id + " successfully")
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update a article by id")
    public ResponseEntity<ApiResponse<ArticleResponse>> updateArticleById(@Positive(message = "must be greater than 0 or must be positive") @PathVariable Integer id, @RequestBody ArticleRequest articleRequest){
        ArticleResponse articleResponse = articleService.updateArticleById(id, articleRequest);
        ApiResponse<ArticleResponse> apiResponse = ApiResponse.<ArticleResponse>builder()
                .message("Update a article with id " + id + " successfully")
                .status(HttpStatus.OK)
                .payload(articleResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

}
