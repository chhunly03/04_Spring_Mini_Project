package com.khrd.spring_boot_mini_project.service;

import com.khrd.spring_boot_mini_project.model.request.userReqest.UserRequest;
import com.khrd.spring_boot_mini_project.model.response.userResponseDTO.UserResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserResponseDTO getUserByEmail(String email);

    UserResponseDTO updateUserByToken(String email, UserRequest updatedUserDetails);
}
