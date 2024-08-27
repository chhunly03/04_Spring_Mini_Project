package com.khrd.spring_boot_mini_project.controller;

import com.khrd.spring_boot_mini_project.model.request.userReqest.UserRequest;
import com.khrd.spring_boot_mini_project.model.response.ApiResponse;
import com.khrd.spring_boot_mini_project.model.response.userResponseDTO.UserResponseDTO;
import com.khrd.spring_boot_mini_project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserByToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        UserResponseDTO user = userService.getUserByEmail(email);

        if (user != null) {
            ApiResponse<UserResponseDTO> response = ApiResponse.<UserResponseDTO>builder()
                    .message("User successfully retrieved")
                    .status(HttpStatus.OK)
                    .payload(user)
                    .build();
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<UserResponseDTO> response = ApiResponse.<UserResponseDTO>builder()
                    .message("User not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUserByToken(@RequestBody UserRequest updatedUserDetails) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            UserResponseDTO updatedUser = userService.updateUserByToken(email,updatedUserDetails);
            ApiResponse<UserResponseDTO> response = ApiResponse.<UserResponseDTO>builder()
                    .message("User successfully updated")
                    .status(HttpStatus.OK)
                    .payload(updatedUser)
                    .build();
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponse<UserResponseDTO> response = ApiResponse.<UserResponseDTO>builder()
                    .message(e.getMessage())
                    .status(HttpStatus.NOT_FOUND)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
