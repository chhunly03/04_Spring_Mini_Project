package com.khrd.spring_boot_mini_project.repository;

import com.khrd.spring_boot_mini_project.model.entity.CategoryArticle;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CategoryArticleRepository extends JpaRepository <CategoryArticle ,Integer> {
   @Transactional
    void deleteByArticleId(Integer id);
}
