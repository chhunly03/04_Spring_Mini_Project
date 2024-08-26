package com.khrd.spring_boot_mini_project.repository;

import com.khrd.spring_boot_mini_project.repository.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
