package com.khrd.spring_boot_mini_project.service.serviceImpl;

import com.khrd.spring_boot_mini_project.exception.NotFoundException;
import com.khrd.spring_boot_mini_project.service.UserImpl.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private final Path path = Paths.get("src/main/resources/images");
    @Override
    public String saveFile(MultipartFile file) throws IOException , NotFoundException {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String fileExtension = StringUtils.getFilenameExtension(fileName).toLowerCase();
        if ("jpg".equals(fileExtension) || "jpeg".equals(fileExtension) || "png".equals(fileExtension)) {
            fileName = UUID.randomUUID() + "." + fileExtension;
            if (!Files.exists(path)){
                Files.createDirectories(path);
            }
            Files.copy(file.getInputStream(), path.resolve(fileName));

            return fileName;

        } else {
            throw new NotFoundException("File must be contain jpg, png, jpeg");
        }
    }
    @Override
    public Resource getFileByFileName(String fileName) throws IOException, NotFoundException {
        Path path = Paths.get("src/main/resources/images/" + fileName);
        if (Files.exists(path)) {
            return new ByteArrayResource(Files.readAllBytes(path));
        } else {
            throw new NotFoundException("File not found");
        }

    }
}
