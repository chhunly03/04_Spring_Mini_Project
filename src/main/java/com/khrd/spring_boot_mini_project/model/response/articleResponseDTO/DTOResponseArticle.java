package com.khrd.spring_boot_mini_project.model.response.articleResponseDTO;

import com.khrd.spring_boot_mini_project.model.entity.Article;
import com.khrd.spring_boot_mini_project.model.response.CommentResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class DTOResponseArticle {
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime createAt;
    private Integer ownerOfArticle;



    public void articleResponseDTO(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.description = article.getDescription();
        this.createAt = article.getCreateAt();
        this.ownerOfArticle = article.getUser().getUserId();
    }
}
