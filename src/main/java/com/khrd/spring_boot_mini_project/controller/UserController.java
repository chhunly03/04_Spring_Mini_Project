package com.khrd.spring_boot_mini_project.controller;

import com.khrd.spring_boot_mini_project.model.entity.User;
import com.khrd.spring_boot_mini_project.model.response.ApiResponce;
import com.khrd.spring_boot_mini_project.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponce<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUser();

        if (users != null && !users.isEmpty()) {
            ApiResponce<List<User>> response = ApiResponce.<List<User>>builder()
                    .message("Successfully retrieved users")
                    .status(HttpStatus.OK)
                    .payload(users)
                    .build();
            return ResponseEntity.ok(response);
        } else {
            ApiResponce<List<User>> response = ApiResponce.<List<User>>builder()
                    .message("No users found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
