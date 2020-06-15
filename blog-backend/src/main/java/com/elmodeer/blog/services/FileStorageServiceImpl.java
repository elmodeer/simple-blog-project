package com.elmodeer.blog.services;

import com.elmodeer.blog.controllers.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final Path root = Paths.get("uploads");


    @Override
    public void init() {
        try {
            Files.createDirectory(root);
            logger.info("Successful Creation");
        } catch (IOException e) {
            logger.error("Un Successful Creation", e);
//            System.out.println(e.getMessage());
        }
    }

    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store file. Error: " + e.getMessage());
        }
    }
}
