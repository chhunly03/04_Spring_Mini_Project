package com.khrd.spring_boot_mini_project.controller;

import com.khrd.spring_boot_mini_project.model.request.auth.AuthRegisterRequest;
import com.khrd.spring_boot_mini_project.model.request.auth.AuthLoginRequest;
import com.khrd.spring_boot_mini_project.model.response.ApiResponse;
import com.khrd.spring_boot_mini_project.model.response.responseAuthDTO.AuthLoginResponseDTO;
import com.khrd.spring_boot_mini_project.model.response.responseAuthDTO.AuthRegisterResponseDTO;
import com.khrd.spring_boot_mini_project.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @Operation(summary = "User registration")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthRegisterResponseDTO>> register(@RequestBody AuthRegisterRequest request) {
        try {
            AuthRegisterResponseDTO user = authService.register(request);

            ApiResponse<AuthRegisterResponseDTO> response = ApiResponse.<AuthRegisterResponseDTO>builder()
                    .message("User created successfully")
                    .status(HttpStatus.CREATED)
                    .payload(user)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<AuthRegisterResponseDTO> response = ApiResponse.<AuthRegisterResponseDTO>builder()
                    .message("User creation failed")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "User login")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthLoginResponseDTO>> login(@RequestBody AuthLoginRequest request) {
        try {
            String token = authService.login(request, authenticationManager);

            AuthLoginResponseDTO loginResponseDTO = new AuthLoginResponseDTO(token);

            ApiResponse<AuthLoginResponseDTO> response = ApiResponse.<AuthLoginResponseDTO>builder()
                    .message("Successfully logged in")
                    .status(HttpStatus.OK)
                    .payload(loginResponseDTO)
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<AuthLoginResponseDTO> response = ApiResponse.<AuthLoginResponseDTO>builder()
                    .message("Login failed")
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
