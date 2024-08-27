package com.khrd.spring_boot_mini_project.model.response.articleResponseDTO;

import com.khrd.spring_boot_mini_project.model.response.category.CategoryListDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ArticleCategoryDTO {
    private Integer articleId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private Integer ownerOfArticle;
    private List<Integer> categoryIdList;
}

