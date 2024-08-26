package com.khrd.spring_boot_mini_project.controller;

import com.khrd.spring_boot_mini_project.exception.NotFoundException;
import com.khrd.spring_boot_mini_project.model.response.FileResponse;
import com.khrd.spring_boot_mini_project.service.UserImpl.FileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/files")
@AllArgsConstructor
public class AppFileController {
    private final FileService fileService;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) throws NotFoundException, IOException {
        String fileName = fileService.saveFile(file);
        FileResponse fileResponse = new FileResponse(fileName,file.getContentType(), file.getSize());
        return ResponseEntity.ok(fileResponse);
    }
    @GetMapping
    public ResponseEntity<?> getFile(@RequestParam String fileName) throws IOException, NotFoundException {
        Resource resource = fileService.getFileByFileName(fileName);
        MediaType mediaType;
        if (fileName.endsWith(".pdf")) {
            mediaType = MediaType.APPLICATION_PDF;
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif")) {
            mediaType = MediaType.IMAGE_PNG;
        } else {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .contentType(mediaType).body(resource);
    }
}
