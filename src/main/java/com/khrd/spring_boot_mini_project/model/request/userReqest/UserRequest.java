package com.khrd.spring_boot_mini_project.model.request.userReqest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserRequest {
    private String username;
    private String email;
    private String address;
    private String phone_number;
    private String password;
    private String role;
}
