package com.khrd.spring_boot_mini_project.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.khrd.spring_boot_mini_project.model.response.userResponseDTO.UserResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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

    @Column(nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    private String role;

    @Column(nullable = false)
    private String password;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private LocalDateTime createAt;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "update_date")

    private LocalDateTime updateAt;

    @OneToMany (mappedBy ="user" ,cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks;
    @OneToMany (mappedBy = "user",cascade = CascadeType.ALL )
    private List<Comment>comments;

    @OneToMany(mappedBy = "user")
    private List<Category> categories;

    public UserResponseDTO toResponse() {
        return new UserResponseDTO(
             this.userId,
             this.username,
             this.email,
                this.address,
                this.phoneNumber,
                this.createAt,
                this.updateAt,
                this.role
        );
    }
}
