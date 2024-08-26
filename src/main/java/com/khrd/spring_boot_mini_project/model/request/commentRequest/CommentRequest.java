package com.khrd.spring_boot_mini_project.model.request.commentRequest;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CommentRequest {
    private String comment;
}
