package com.khrd.spring_boot_mini_project.service.UserImpl;

import com.khrd.spring_boot_mini_project.exception.NotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String saveFile(MultipartFile file) throws IOException , NotFoundException;
    Resource getFileByFileName(String fileName) throws IOException , NotFoundException;
}
