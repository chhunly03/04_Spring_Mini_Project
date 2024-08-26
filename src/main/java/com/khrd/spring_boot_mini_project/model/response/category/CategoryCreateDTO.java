package com.khrd.spring_boot_mini_project.model.response.category;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class CategoryCreateDTO {
    private Integer categoryId;
    private String categoryName;
    private LocalDateTime createdAt;
}
