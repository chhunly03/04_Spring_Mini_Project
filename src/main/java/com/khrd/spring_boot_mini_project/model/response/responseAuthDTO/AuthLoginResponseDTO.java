package com.khrd.spring_boot_mini_project.model.response.responseAuthDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthLoginResponseDTO {
    private String token;
}
