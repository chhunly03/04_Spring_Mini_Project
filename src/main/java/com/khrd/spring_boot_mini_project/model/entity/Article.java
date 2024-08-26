package com.khrd.spring_boot_mini_project.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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
    private Date createAt;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "update_date")
    private Date updateAt;

    @ManyToOne
    private User user;
    @OneToMany (mappedBy = "article", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<CategoryArticle>categoryArticles;
    @OneToMany (mappedBy = "article", fetch = FetchType.LAZY,cascade =CascadeType.ALL)
    private List<Comment> comments;
    @OneToMany (mappedBy = "article", fetch = FetchType.LAZY,cascade =CascadeType.ALL)
    private List<Bookmark> bookmarks;




}
