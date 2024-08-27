package com.khrd.spring_boot_mini_project.service;

import com.khrd.spring_boot_mini_project.model.request.category.CategoryRequest;
import com.khrd.spring_boot_mini_project.model.response.category.CategoryCreateDTO;
import com.khrd.spring_boot_mini_project.model.response.category.CategoryListDTO;

import java.util.List;


public interface CategoryService {
    CategoryCreateDTO addCategory(CategoryRequest categoryRequest);

    List<CategoryListDTO> getAllCategory(Integer pageNo, Integer pageSize, String sortBy, String sortDirection);

    CategoryListDTO getCategoryById(Integer id);

    CategoryCreateDTO updateCategoryById(Integer id, CategoryRequest categoryRequest);

    void deleteCategoryById(Integer id);
}
