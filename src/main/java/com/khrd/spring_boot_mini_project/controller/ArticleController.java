package com.khrd.spring_boot_mini_project.controller;

import com.khrd.spring_boot_mini_project.model.request.articleRequest.ArticleRequest;
import com.khrd.spring_boot_mini_project.model.response.ApiResponce;
import com.khrd.spring_boot_mini_project.model.response.articleResponseDTO.DTOResponseArticle;
import com.khrd.spring_boot_mini_project.service.ArticleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Data
@RequestMapping("/api/v1")
@SecurityRequirement(name = "bearerAuth")
public class ArticleController {
    private final ArticleService articleService;

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
}
