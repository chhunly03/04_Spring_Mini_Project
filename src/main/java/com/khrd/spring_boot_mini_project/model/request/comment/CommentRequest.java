package com.khrd.spring_boot_mini_project.model.request.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.khrd.spring_boot_mini_project.repository.entity.Article;
import com.khrd.spring_boot_mini_project.repository.entity.Comment;
import com.khrd.spring_boot_mini_project.repository.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class CommentRequest {
    @NotNull
    @NotBlank
    @NotEmpty
    private String comment;

    public Comment toEntity() {
        return new Comment(
                null,
                this.comment,
                LocalDateTime.now(),
                LocalDateTime.now(),
                null,
                null
        );
    }
}
