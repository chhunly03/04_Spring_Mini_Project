package com.khrd.spring_boot_mini_project.model.entity;

import com.khrd.spring_boot_mini_project.model.response.category.CategoryCreateDTO;
import com.khrd.spring_boot_mini_project.model.response.category.CategoryListDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "\"category\"")
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

    @OneToMany(mappedBy = "category")
    private List<CategoryArticle>categoryArticles;
}
