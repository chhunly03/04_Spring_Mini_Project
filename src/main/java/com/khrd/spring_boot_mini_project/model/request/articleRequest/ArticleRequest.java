package com.khrd.spring_boot_mini_project.model.request.articleRequest;

import com.khrd.spring_boot_mini_project.repository.entity.Article;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ArticleRequest {
    private String title;
    private String description;
    private List<Integer> CategoryId;

public Article toArticleEntry() {
    return new Article(null,this.title,this.description, LocalDateTime.now(),LocalDateTime.now(),null,null,null, null);

}


}
