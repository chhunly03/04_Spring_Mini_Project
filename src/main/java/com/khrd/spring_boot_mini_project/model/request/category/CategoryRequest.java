package com.khrd.spring_boot_mini_project.model.request.category;

import com.khrd.spring_boot_mini_project.model.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class CategoryRequest {
    @NotEmpty
    @NotNull
    @NotBlank
    private String categoryName;

    public Category toEntity() {
        return new Category(null, this.categoryName, null, LocalDateTime.now(), LocalDateTime.now(), null);
    }
}
