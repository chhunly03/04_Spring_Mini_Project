package com.khrd.spring_boot_mini_project.model.response.bookmarkResponse;

import com.khrd.spring_boot_mini_project.model.entity.Comment;
import com.khrd.spring_boot_mini_project.model.entity.User;
import com.khrd.spring_boot_mini_project.model.response.userResponseDTO.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BookMarkResponseDTO {
    private Integer articleId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer ownerOfArticle;
    private List<Integer> categoryIdList;
    private List<CommentListDTO> commentList;
}
