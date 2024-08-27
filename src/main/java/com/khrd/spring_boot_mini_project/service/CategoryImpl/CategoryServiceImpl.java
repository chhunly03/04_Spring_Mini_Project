package com.khrd.spring_boot_mini_project.service.CategoryImpl;

import com.khrd.spring_boot_mini_project.exception.NotFoundException;
import com.khrd.spring_boot_mini_project.model.entity.Category;
import com.khrd.spring_boot_mini_project.model.request.category.CategoryRequest;
import com.khrd.spring_boot_mini_project.model.response.category.CategoryCreateDTO;
import com.khrd.spring_boot_mini_project.model.response.category.CategoryListDTO;
import com.khrd.spring_boot_mini_project.repository.CategoryRepository;
import com.khrd.spring_boot_mini_project.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryCreateDTO addCategory(CategoryRequest categoryRequest) {
        return categoryRepository.save(categoryRequest.toEntity(0)).toResponseCreate();
    }

    @Override
    public List<CategoryListDTO> getAllCategory(Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {
        Page<Category> paginate = categoryRepository.findAll(PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy)));
        return paginate.getContent().stream().map(Category::toResponseList).toList();
    }

    @Override
    public CategoryListDTO getCategoryById(Integer id) {
        if (categoryRepository.findById(id).isPresent()) {
            return categoryRepository.findById(id).get().toResponseList();
        }
        throw new NotFoundException("Category id : " + id + " not found");
    }

    @Override
    public CategoryCreateDTO updateCategoryById(Integer id, CategoryRequest x) {
        if (categoryRepository.findById(id).isPresent()) {
            Category getCategory = categoryRepository.findById(id).get();
            getCategory.setCategoryName(x.getCategoryName());
            getCategory.setUpdatedAt(LocalDateTime.now());
            return categoryRepository.save(getCategory).toResponseCreate();
        }
        throw new NotFoundException("Category id : " + id + " not found");
    }

    @Override
    public void deleteCategoryById(Integer id) {
        if(categoryRepository.findById(id).isPresent()) {
            categoryRepository.deleteById(id);
        }
        throw new NotFoundException("Category id : " + id + " not found");
    }
}
