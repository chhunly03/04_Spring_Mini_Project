package com.khrd.spring_boot_mini_project.controller;

import com.khrd.spring_boot_mini_project.model.request.comment.CommentRequest;
import com.khrd.spring_boot_mini_project.model.response.ApiResponse;
import com.khrd.spring_boot_mini_project.model.response.category.CategoryDeleteDTO;
import com.khrd.spring_boot_mini_project.model.response.comment.CommentDTO;
import com.khrd.spring_boot_mini_project.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/comment")
@SecurityRequirement(name = "bearerAuth")
public class CommentController {
    private CommentService commentService;

    @GetMapping("{id}")
    public ResponseEntity<?> getCommentById(@PathVariable @Positive Integer id) {
        ApiResponse<?> res = ApiResponse.<CommentDTO>builder()
                .message("Successfully get comment id : " + id)
                .status(HttpStatus.OK)
                .payload(commentService.getCommentById(id))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCommentById(@PathVariable @Positive Integer id, @RequestBody @Valid CommentRequest x) {
        ApiResponse<?> res = ApiResponse.<CommentDTO>builder()
                .message("Successfully update comment id : " + id)
                .status(HttpStatus.OK)
                .payload(commentService.updateCommentById(id, x))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable @Positive Integer id) {
        commentService.deleteCommentById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<CategoryDeleteDTO>builder()
                        .message("Comment deleted")
                        .payload(CategoryDeleteDTO.builder()
                                .message("Successfully delete comment by id " + id)
                                .build())
                        .build()
        );
    }
}
