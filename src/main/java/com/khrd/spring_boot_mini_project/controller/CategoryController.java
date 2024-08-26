package com.khrd.spring_boot_mini_project.controller;

import com.khrd.spring_boot_mini_project.model.entity.Category;
import com.khrd.spring_boot_mini_project.model.request.category.CategoryRequest;
import com.khrd.spring_boot_mini_project.model.response.ApiResponce;
import com.khrd.spring_boot_mini_project.model.response.category.CategoryCreateDTO;
import com.khrd.spring_boot_mini_project.model.response.category.CategoryListDTO;
import com.khrd.spring_boot_mini_project.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        ApiResponce<?> response = ApiResponce.<CategoryCreateDTO>builder()
                .message("Successfully created new category")
                .status(HttpStatus.CREATED)
                .payload(categoryService.addCategory(categoryRequest))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllCategories(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "categoryId") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
            ) {
        ApiResponce<?> response = ApiResponce.<List<CategoryListDTO>>builder()
                .message("Successfully retrieved all")
                .status(HttpStatus.OK)
                .payload(categoryService.getAllCategory(pageNo, pageSize, sortBy, sortDirection))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable @Positive Integer id) {
        ApiResponce<?> response = ApiResponce.<CategoryListDTO>builder()
                .message("Successfully retrieved category with id " + id)
                .status(HttpStatus.OK)
                .payload(categoryService.getCategoryById(id))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCategoryById(
            @PathVariable Integer id,
            @RequestBody @Valid CategoryRequest categoryRequest
    ) {
        ApiResponce<?> response = ApiResponce.<CategoryCreateDTO>builder()
                .message("Successfully updated category with id " + id)
                .status(HttpStatus.OK)
                .payload(categoryService.updateCategoryById(id, categoryRequest))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable @Positive Integer id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted category with id " + id);
    }
}
