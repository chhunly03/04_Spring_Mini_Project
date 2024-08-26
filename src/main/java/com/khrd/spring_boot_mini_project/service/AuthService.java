package com.khrd.spring_boot_mini_project.service;

import com.khrd.spring_boot_mini_project.model.request.auth.AuthRegisterRequest;
import com.khrd.spring_boot_mini_project.model.request.auth.AuthLoginRequest;
import com.khrd.spring_boot_mini_project.model.response.responseAuthDTO.AuthRegisterResponseDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface AuthService extends UserDetailsService {

    AuthRegisterResponseDTO register(AuthRegisterRequest userRequest);

    String login(AuthLoginRequest userLoginRequest, AuthenticationManager authenticationManager);
}
