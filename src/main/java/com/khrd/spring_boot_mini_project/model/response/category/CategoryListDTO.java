package com.khrd.spring_boot_mini_project.model.response.category;

import com.khrd.spring_boot_mini_project.model.response.ArticleResponse;
import com.khrd.spring_boot_mini_project.model.response.articleResponseDTO.ArticleCategoryDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class CategoryListDTO {
    private Integer categoryId;
    private String categoryName;
    private Integer amountOfArticles;
    private LocalDateTime createdAt;
    private List<ArticleCategoryDTO> articleList;
}
