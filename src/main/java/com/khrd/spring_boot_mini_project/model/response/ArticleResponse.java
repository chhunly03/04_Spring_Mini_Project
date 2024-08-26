package com.khrd.spring_boot_mini_project.model.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ArticleResponse {
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime createAt;
    private Integer ownerOfArticle;
    private List<Integer> categoryIdList;
    private List<CommentResponse> commentResponsesList;
}
