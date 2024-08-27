package com.khrd.spring_boot_mini_project.model.response.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CategoryCreateDTO {
    private Integer categoryId;
    private String categoryName;
    private LocalDateTime createdAt;
}
