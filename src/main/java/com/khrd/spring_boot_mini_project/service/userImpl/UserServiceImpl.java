package com.khrd.spring_boot_mini_project.service.userImpl;

import com.khrd.spring_boot_mini_project.model.entity.User;
import com.khrd.spring_boot_mini_project.model.request.userReqest.UserRequest;
import com.khrd.spring_boot_mini_project.model.response.userResponseDTO.UserResponseDTO;
import com.khrd.spring_boot_mini_project.repository.UserRepository;
import com.khrd.spring_boot_mini_project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Primary
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return new UserResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateUserByToken(String email, UserRequest updatedUserDetails) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (updatedUserDetails.getUsername() != null) {
                user.setUsername(updatedUserDetails.getUsername());
            }
            if (updatedUserDetails.getEmail() != null){
                user.setEmail(updatedUserDetails.getEmail());
            }
            if (updatedUserDetails.getAddress() != null) {
                user.setAddress(updatedUserDetails.getAddress());
            }
            if (updatedUserDetails.getPhone_number() != null) {
                user.setPhoneNumber(updatedUserDetails.getPhone_number());
            }
            if (updatedUserDetails.getPassword() != null) {
                String hashedPassword = passwordEncoder.encode(updatedUserDetails.getPassword());
                user.setPassword(hashedPassword);
            }
            if (updatedUserDetails.getRole() != null) {
                user.setRole(updatedUserDetails.getRole());
            }
            User savedUser = userRepository.save(user);

            return new UserResponseDTO(savedUser);
        } else {
            throw new RuntimeException("User with email " + email + " not found");
        }
    }
}
