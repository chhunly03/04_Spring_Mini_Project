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
    private String phone_number;
    @Column(name = "date")
    private LocalDateTime create_at;
    @Column(name = "update_date")
    private LocalDateTime update_at;
    private String role;

    public UserResponseDTO(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.phone_number = user.getPhoneNumber();
        this.create_at = user.getCreatedAt();
        this.update_at = user.getUpdatedAt();
        this.role = user.getRole();
    }
}
