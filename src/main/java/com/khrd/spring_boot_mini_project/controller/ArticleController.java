package com.khrd.spring_boot_mini_project.controller;

import com.khrd.spring_boot_mini_project.model.request.articleRequest.ArticleRequest;
import com.khrd.spring_boot_mini_project.model.response.ApiResponce;
import com.khrd.spring_boot_mini_project.model.response.ArticleResponse;
import com.khrd.spring_boot_mini_project.model.response.articleResponseDTO.DTOResponseArticle;
import com.khrd.spring_boot_mini_project.service.ArticleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping
    public ResponseEntity<ApiResponce<List<ArticleResponse>>> getAllArticle(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction

    ){
    List<ArticleResponse> articleResponses =articleService.getAllArticle(page, size, sortBy, direction);
    ApiResponce<List<ArticleResponse>> apiResponce = ApiResponce.<List<ArticleResponse>>builder()
            .message("Article created successfully")
            .status(HttpStatus.OK)
            .payload(articleResponses)
            .build();
        return ResponseEntity.ok(apiResponce);
    }
}
