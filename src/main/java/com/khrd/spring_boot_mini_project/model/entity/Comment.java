package com.khrd.spring_boot_mini_project.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khrd.spring_boot_mini_project.model.response.bookmarkResponse.CommentListDTO;
import com.khrd.spring_boot_mini_project.model.response.comment.CommentDTO;
import com.khrd.spring_boot_mini_project.model.response.userResponseDTO.UserResponseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    @JsonIgnore
    private Article article;
    @ManyToOne
    @JsonIgnore
    private User user;

    public Comment(Integer id, String cmt, LocalDateTime createAt, User user) {
    }

    public CommentDTO toResponseV1() {
        return new CommentDTO(
                this.id,
                this.cmt,
                this.createAt,
                this.user.toResponseD()
        );
    }

    public CommentListDTO toResponse() {
        return new CommentListDTO(
              this.id,
              this.cmt,
              this.createAt,
              this.user.toResponseD()
        );
    }

}
