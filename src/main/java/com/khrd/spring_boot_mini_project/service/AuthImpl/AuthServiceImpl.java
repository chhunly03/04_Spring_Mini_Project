package com.khrd.spring_boot_mini_project.service.AuthImpl;

import com.khrd.spring_boot_mini_project.exception.BadRequestException;
import com.khrd.spring_boot_mini_project.jwt.JwtService;
import com.khrd.spring_boot_mini_project.repository.entity.User;
import com.khrd.spring_boot_mini_project.model.request.auth.AuthRegisterRequest;
import com.khrd.spring_boot_mini_project.model.request.auth.AuthLoginRequest;
import com.khrd.spring_boot_mini_project.model.response.responseAuthDTO.AuthRegisterResponseDTO;
import com.khrd.spring_boot_mini_project.repository.AuthRepository;
import com.khrd.spring_boot_mini_project.service.AuthService;
import com.khrd.spring_boot_mini_project.model.userDetail.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthRegisterResponseDTO register(AuthRegisterRequest authRequest) {
        System.out.println(authRequest);
        if (userRepository.findByEmail(authRequest.getEmail()) != null) {
            throw new BadRequestException("Email is already in use.");
        }
        // Encode the password
        authRequest.setPassword(passwordEncoder.encode(authRequest.getPassword()));

        User newUser = getUser(authRequest);

        userRepository.save(newUser);
        return AuthRegisterResponseDTO.fromUser(newUser);
    }

    private static User getUser(AuthRegisterRequest userRequest) {
        User newUser = new User();
        newUser.setUsername(userRequest.getUsername());
        newUser.setEmail(userRequest.getEmail());
        newUser.setPassword(userRequest.getPassword());
        newUser.setAddress(userRequest.getAddress());
        newUser.setPhoneNumber(userRequest.getPhoneNumber());
        newUser.setCreateAt(LocalDateTime.now());
        newUser.setUpdateAt(LocalDateTime.now());

        String role = userRequest.getRole() != null && userRequest.getRole().equalsIgnoreCase("AUTHOR")
                ? "AUTHOR" : "READER";
        newUser.setRole(role);
        return newUser;
    }


    @Override
    public String login(AuthLoginRequest userLoginRequest, AuthenticationManager authenticationManager) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequest.getEmail(), userLoginRequest.getPassword())
        );

        // Load the user details and generate a JWT token
        UserDetails userDetails = loadUserByUsername(userLoginRequest.getEmail());

        return jwtService.generateToken(userDetails);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new BadCredentialsException("User not found.");
        }
        return new CustomUserDetails(user);
    }
}
