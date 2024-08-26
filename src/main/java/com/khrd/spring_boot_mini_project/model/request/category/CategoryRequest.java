package com.khrd.spring_boot_mini_project.model.request.category;

import com.khrd.spring_boot_mini_project.model.entity.Category;
import com.khrd.spring_boot_mini_project.model.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotEmpty
    @NotNull
    @NotBlank
    private String categoryName;

    public Category toEntity() {
        return new Category(null, this.categoryName, null, LocalDateTime.now(), LocalDateTime.now(), null);
    }
}