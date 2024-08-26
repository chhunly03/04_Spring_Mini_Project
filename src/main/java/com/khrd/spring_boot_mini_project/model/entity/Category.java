package com.khrd.spring_boot_mini_project.model.entity;

import com.khrd.spring_boot_mini_project.model.response.category.CategoryCreateDTO;
import com.khrd.spring_boot_mini_project.model.response.category.CategoryListDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    private String categoryName;
    private Integer amountOfArticles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public CategoryCreateDTO toResponseCreate() {
        return new CategoryCreateDTO(this.categoryId, this.categoryName, this.createdAt);
    }

    public CategoryListDTO toResponseList() {
        return new CategoryListDTO(this.categoryId, this.categoryName, this.amountOfArticles, this.createdAt);
    }
}
