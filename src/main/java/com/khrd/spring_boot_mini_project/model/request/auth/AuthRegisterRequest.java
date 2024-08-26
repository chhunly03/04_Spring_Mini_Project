package com.khrd.spring_boot_mini_project.model.request.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthRegisterRequest {
        private String username;
        private String email;
        private String role;
        private String password;
        private String address;
        private String phoneNumber;
}
