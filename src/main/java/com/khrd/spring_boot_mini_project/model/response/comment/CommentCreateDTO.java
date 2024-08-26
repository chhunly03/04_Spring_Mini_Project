package com.khrd.spring_boot_mini_project.model.response.comment;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class CommentCreateDTO {
    private Integer articleId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private Integer ownerOfArticle;
    private List<Integer> categoryIdList;
    private List<CommentDTO> commentList;
}
