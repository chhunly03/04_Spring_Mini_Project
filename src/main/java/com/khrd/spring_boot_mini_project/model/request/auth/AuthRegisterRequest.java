package com.khrd.spring_boot_mini_project.model.request.auth;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class AuthRegisterRequest {

        @NotBlank(message = "Username must be provided")
        @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
        private String username;

        @NotBlank(message = "Email must be provided")
        @Email(message = "Invalid email address.")
        private String email;

        @NotBlank(message = "Password must be provided")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must be at least 8 characters long, contain an uppercase letter, a lowercase letter, a digit, and a special character")
        private String password;

        private String address;

        @NotBlank(message = "Phone number must be provided")
        @Pattern(regexp = "^\\d{3}[- ]\\d{3}[- ]\\d{3}$", message = "Phone number must follow the format 012-333-222 or 012 333 222")
        private String phoneNumber;

        @NotBlank(message = "Role must be provided")
        @Pattern(regexp = "^(AUTHOR|READER)$", message = "Role must be either AUTHOR or READER")
        private String role;
}
