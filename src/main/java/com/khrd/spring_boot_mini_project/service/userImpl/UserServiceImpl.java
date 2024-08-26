package com.khrd.spring_boot_mini_project.service.userImpl;

import com.khrd.spring_boot_mini_project.model.entity.User;
import com.khrd.spring_boot_mini_project.repository.UserRepository;
import com.khrd.spring_boot_mini_project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
