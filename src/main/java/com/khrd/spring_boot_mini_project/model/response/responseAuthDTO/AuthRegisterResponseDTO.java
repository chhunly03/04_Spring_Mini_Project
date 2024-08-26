package com.khrd.spring_boot_mini_project.model.response.responseAuthDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.khrd.spring_boot_mini_project.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthRegisterResponseDTO {
    private Integer userId;
    private String username;
    private String email;
    private String address;
    private String phoneNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdAt;
    private String role;

    public static AuthRegisterResponseDTO fromUser(User user) {
        return new AuthRegisterResponseDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getCreate_at(),
                user.getRole()
        );
    }
}