package com.khrd.spring_boot_mini_project.model.response.userResponseDTO;

import com.khrd.spring_boot_mini_project.model.entity.User;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserResponseDTO {
    private Integer userId;
    private String username;
    private String email;
    private String address;
    private String phoneNumber;
    @Column(name = "date")
    private LocalDateTime createAt;
    @Column(name = "update_date")
    private LocalDateTime updateAt;
    private String role;

    public UserResponseDTO(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.phoneNumber = user.getPhoneNumber();
        this.createAt = user.getCreateAt();
        this.updateAt = user.getUpdateAt();
        this.role = user.getRole();
    }
}
