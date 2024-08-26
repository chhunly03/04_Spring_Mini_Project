package com.khrd.spring_boot_mini_project.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.khrd.spring_boot_mini_project.model.response.articleResponseDTO.DTOResponseArticle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name ="article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private  String description;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private LocalDateTime createAt;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "update_date")
    private LocalDateTime updateAt;

    @ManyToOne
    private User user;
    @OneToMany (mappedBy = "article", cascade = CascadeType.ALL)
    private List<CategoryArticle> categoryArticles;
    @OneToMany (mappedBy = "article", cascade =CascadeType.ALL)
    private List<Comment> comments;
    @OneToMany (mappedBy = "article", cascade =CascadeType.ALL)
    private List<Bookmark> bookmarks;

    public DTOResponseArticle dtoResponse(Integer userId){
        return new DTOResponseArticle(this.id,this.title,this.description,this.createAt,userId );
    }
//    public ArticleResponse toResponse(Integer userId){
//        List<Integer> categoryIds = categoryArticles.stream().map(categoryArticle -> categoryArticle.getCategory().getCategoryId()).toList();
//        return new ArticleResponse(this.id,this.title,this.description,this.createAt,userId, categoryIds, null);
//
//    }




}