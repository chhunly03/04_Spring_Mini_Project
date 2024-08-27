package com.khrd.spring_boot_mini_project.model.request.category;

import com.khrd.spring_boot_mini_project.model.entity.Category;
import com.khrd.spring_boot_mini_project.model.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[a-zA-Z]+$", message = "The category name must be a single word with only alphabetic characters, no spaces, numbers, or special characters.")
    private String categoryName;

    public Category toEntity(Integer amountOfArticles) {
        return new Category(null, this.categoryName, amountOfArticles, LocalDateTime.now(), LocalDateTime.now(), null, null);
    }
}
