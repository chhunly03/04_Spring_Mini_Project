package com.khrd.spring_boot_mini_project.service;

import com.khrd.spring_boot_mini_project.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> getAllUser();
}
