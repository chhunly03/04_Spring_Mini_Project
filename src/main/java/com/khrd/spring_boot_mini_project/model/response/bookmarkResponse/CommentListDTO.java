package com.khrd.spring_boot_mini_project.model.response.bookmarkResponse;

import com.khrd.spring_boot_mini_project.model.response.userResponseDTO.UserResponseDTO;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class CommentListDTO {
    private Integer commentId;
    private String comment;
    private LocalDateTime createdAt;
    private UserResponseDTO user;
}
