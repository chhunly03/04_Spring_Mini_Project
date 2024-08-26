package com.khrd.spring_boot_mini_project.model.request.category;

import com.khrd.spring_boot_mini_project.model.entity.Category;
import com.khrd.spring_boot_mini_project.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CategoryRequest {
    private String categoryName;

    public Category toEntity() {
        return new Category(null, this.categoryName, null, LocalDateTime.now(), LocalDateTime.now(), null);
    }
}
