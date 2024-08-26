package com.khrd.spring_boot_mini_project.model.response;

import com.khrd.spring_boot_mini_project.model.response.userResponseDTO.UserResponseDTO;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CommentResponse {
    private Integer commentId;
    private String comment;
    private LocalDateTime createAt;
    private UserResponseDTO userResponseDTO;
}
