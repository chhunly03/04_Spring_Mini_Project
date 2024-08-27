package com.khrd.spring_boot_mini_project.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.khrd.spring_boot_mini_project.model.response.userResponseDTO.UserResponseDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

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

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "update_date")
    private LocalDateTime updatedAt;

    @OneToMany (mappedBy ="user" ,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks;
    @OneToMany (mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.ALL )
    private List<Comment>comments;

    @OneToMany(mappedBy = "user")
    private List<Category> categories;

    public UserResponseDTO toResponseD() {
            return new UserResponseDTO(
                    this.userId,
                    this.username,
                    this.email,
                    this.address,
                    this.phoneNumber,
                    this.createdAt,
                    LocalDateTime.now(),
                    this.role
            );
    }
}
