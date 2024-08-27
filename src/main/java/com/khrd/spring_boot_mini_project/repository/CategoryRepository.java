package com.khrd.spring_boot_mini_project.repository;

import com.khrd.spring_boot_mini_project.model.entity.Category;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT b FROM Category b WHERE b.user.userId = :userId")
    List<Category> findAllCategoryByUserId(Integer userId, PageRequest pageRequest);

    @Query("SELECT b FROM Category b WHERE b.categoryId = :cateId AND b.user.userId = :userId")
    Category findCategoryIdByUserId(Integer cateId, Integer userId);

    @Query("SELECT b FROM Category b WHERE b.categoryName = :cateName AND b.user.userId = :userId")
    Category findCategoryNameByUserId(String cateName, Integer userId);
}
