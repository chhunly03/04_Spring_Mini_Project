package com.khrd.spring_boot_mini_project.controller;

import com.khrd.spring_boot_mini_project.model.entity.Bookmark;
import com.khrd.spring_boot_mini_project.model.response.ApiResponse;
import com.khrd.spring_boot_mini_project.model.response.bookmarkResponse.BookMarkResponseDTO;
import com.khrd.spring_boot_mini_project.service.BookMarkService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/bookmark/")
public class BookMarkController {
    private final BookMarkService bookMarkService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookMarkResponseDTO>>> getAllBookMark(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "articleId") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getName();
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        List<BookMarkResponseDTO> product = bookMarkService.getAllBookMark(pageable);

        ApiResponse<List<BookMarkResponseDTO>> response = ApiResponse.<List<BookMarkResponseDTO>>builder()
                .status(HttpStatus.OK)
                .message("Get all bookmarked articles successfully.")
                .payload(product)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<Bookmark>>createBookMark(@Param("id") Integer article_id){
        Bookmark storeBookmark = bookMarkService.createBookMark(article_id);
        ApiResponse<Bookmark> response = ApiResponse.<Bookmark>builder()
                .message("An article id "+article_id+" is bookmarked successfully.")
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{bookmarkId}")
    public ResponseEntity<ApiResponse<Bookmark>> updateBookmark(@PathVariable("bookmarkId") Integer bookmarkId) {
        System.out.println("Check id" + bookmarkId);
        Bookmark bookmark = bookMarkService.updateBookmark(bookmarkId);
        ApiResponse<Bookmark> response = ApiResponse.<Bookmark>builder()
                .message("Bookmark with id " + bookmarkId + " updated successfully.")
                .status(HttpStatus.OK)
                .payload(null)
                .build();
        return ResponseEntity.ok(response);
    }
}
