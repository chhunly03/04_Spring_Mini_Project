package com.khrd.spring_boot_mini_project.controller;

import com.khrd.spring_boot_mini_project.model.entity.User;
import com.khrd.spring_boot_mini_project.model.response.ApiResponce;
import com.khrd.spring_boot_mini_project.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponce<User>> getUserByToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userService.getUserByEmail(email);

        if (user != null) {
            ApiResponce<User> response = ApiResponce.<User>builder()
                    .message("User successfully retrieved")
                    .status(HttpStatus.OK)
                    .payload(user)
                    .build();
            return ResponseEntity.ok(response);
        } else {
            ApiResponce<User> response = ApiResponce.<User>builder()
                    .message("User not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
