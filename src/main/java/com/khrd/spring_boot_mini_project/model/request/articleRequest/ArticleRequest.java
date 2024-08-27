package com.khrd.spring_boot_mini_project.model.request.articleRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ArticleRequest {
    @NotBlank(message = "Title cannot be empty")
    @NotNull
    private String title;
    @NotBlank(message = "Description cannot be empty")
    @NotNull
    private String description;
    private List<Integer> CategoryId;

}
