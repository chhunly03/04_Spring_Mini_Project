package com.khrd.spring_boot_mini_project.model.entity;

import com.khrd.spring_boot_mini_project.model.userDetail.CustomUserDetails;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.context.SecurityContextHolder;

public class GetCurrentUser {

    public static Integer userId(){
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUser().getUserId();
    }
}
