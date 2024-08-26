package com.khrd.spring_boot_mini_project.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.khrd.spring_boot_mini_project.model.response.comment.CommentCreateDTO;
import com.khrd.spring_boot_mini_project.model.response.comment.CommentDTO;
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
@Table(name ="comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String cmt;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private LocalDateTime createAt;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "update_date")
    private LocalDateTime updateAt;
    @ManyToOne
    private Article article;
    @ManyToOne
    private User user;

    public CommentDTO toResponse() {
        return new CommentDTO(
                this.id,
                this.cmt,
                this.createAt,
                this.user.toResponse()
        );
    }

    public CommentCreateDTO toResponseCreate() {
        return new CommentCreateDTO(
                this.article.getId(),
                this.article.getTitle(),
                this.article.getDescription(),
                this.createAt,
                this.user.getUserId(),
                this.article.getCategoryArticles().stream().map(x -> x.getCategory().getCategoryId()).toList(),
                this.article.getComments().stream().map(Comment::toResponse).toList()
        );
    }
}
