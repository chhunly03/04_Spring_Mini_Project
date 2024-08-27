package com.khrd.spring_boot_mini_project.service.CategoryImpl;

import com.khrd.spring_boot_mini_project.exception.NotFoundException;
import com.khrd.spring_boot_mini_project.model.entity.Category;
import com.khrd.spring_boot_mini_project.model.userDetail.CustomUserDetails;
import com.khrd.spring_boot_mini_project.repository.UserRepository;
import com.khrd.spring_boot_mini_project.model.request.category.CategoryRequest;
import com.khrd.spring_boot_mini_project.model.response.category.CategoryCreateDTO;
import com.khrd.spring_boot_mini_project.model.response.category.CategoryListDTO;
import com.khrd.spring_boot_mini_project.repository.CategoryRepository;
import com.khrd.spring_boot_mini_project.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public CategoryCreateDTO addCategory(CategoryRequest categoryRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Integer getUser = customUserDetails.getUserId();

        return categoryRepository.save(categoryRequest.toEntity(0, userRepository.findById(getUser).get())).toResponseCreate();
    }

    @Override
    public List<CategoryListDTO> getAllCategory(Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Integer getUser = customUserDetails.getUserId();

        List<Category> paginate = categoryRepository.findAllCategoryByUserId(getUser,PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy)));
        return paginate.stream().map(Category::toResponseList).toList();
    }

    @Override
    public CategoryListDTO getCategoryById(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Integer getUser = customUserDetails.getUserId();

        if (categoryRepository.findCategoryIdByUserId(id, getUser) != null) {
            return categoryRepository.findCategoryIdByUserId(id, getUser).toResponseList();
        }
        throw new NotFoundException("Category id : " + id + " not found");
    }

    @Override
    public CategoryCreateDTO updateCategoryById(Integer id, CategoryRequest x) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Integer getUser = customUserDetails.getUserId();

        if (categoryRepository.findCategoryIdByUserId(id, getUser) != null) {
            Category getCategory = categoryRepository.findCategoryIdByUserId(id, getUser);
            getCategory.setCategoryName(x.getCategoryName());
            getCategory.setUpdatedAt(LocalDateTime.now());
            return categoryRepository.save(getCategory).toResponseCreate();
        }
        throw new NotFoundException("Category id : " + id + " not found");
    }

    @Override
    public void deleteCategoryById(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Integer getUser = customUserDetails.getUserId();

        if(categoryRepository.findCategoryIdByUserId(id, getUser) != null) {
            categoryRepository.deleteById(id);
            return;
        }
        throw new NotFoundException("Category id : " + id + " not found");
    }
}