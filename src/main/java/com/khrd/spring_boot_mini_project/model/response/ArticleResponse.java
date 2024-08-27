package com.khrd.spring_boot_mini_project.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleResponse {
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime createAt;
    private Integer ownerOfArticle;
    private List<Integer> categoryIdList;
    private List<CommentResponse> commentResponsesList;
}
