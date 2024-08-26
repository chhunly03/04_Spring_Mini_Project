package com.khrd.spring_boot_mini_project.controller;

import com.khrd.spring_boot_mini_project.model.request.articleRequest.ArticleRequest;
import com.khrd.spring_boot_mini_project.model.request.comment.CommentRequest;
import com.khrd.spring_boot_mini_project.model.response.ApiResponce;
import com.khrd.spring_boot_mini_project.model.response.articleResponseDTO.DTOResponseArticle;
import com.khrd.spring_boot_mini_project.model.response.comment.CommentCreateDTO;
import com.khrd.spring_boot_mini_project.service.ArticleService;
import com.khrd.spring_boot_mini_project.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Data
@RequestMapping("/api/v1/article")
@SecurityRequirement(name = "bearerAuth")
public class ArticleController {
    private final ArticleService articleService;
    private final CommentService commentService;

    @PostMapping()
    public ResponseEntity<ApiResponce<DTOResponseArticle>> createArticle(@RequestBody ArticleRequest articleRequest){
        DTOResponseArticle dtoResponseArticle =articleService.createArticle(articleRequest);
        ApiResponce<DTOResponseArticle> apiResponce = ApiResponce.<DTOResponseArticle>builder()
                .message("Article created successfully")
                .status(HttpStatus.CREATED)
                .payload(dtoResponseArticle)
                .build();
        return ResponseEntity.ok(apiResponce);
    }

    @GetMapping("{id}/comment")
    public ResponseEntity<?> getCommentOnArticle(
            @PathVariable @Positive Integer id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("{id}/comment")
    public ResponseEntity<?> createCommentOnArticle(
            @RequestBody @Valid CommentRequest x,
            @PathVariable @Positive Integer id
    ) {
        System.out.println("it calling");
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponce.<CommentCreateDTO>builder()
                .message("Successfully created new comment on article")
                .status(HttpStatus.CREATED)
                .payload(commentService.createCommentArticle(id, x))
                .build());
    }
}
