package com.khrd.spring_boot_mini_project.model.request.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthLoginRequest {
    private String email;
    private String password;
}
