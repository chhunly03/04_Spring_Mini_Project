package com.khrd.spring_boot_mini_project.model.request.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
}
