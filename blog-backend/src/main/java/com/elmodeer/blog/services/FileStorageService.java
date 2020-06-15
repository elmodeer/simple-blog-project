package com.elmodeer.blog.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    public void init();
    public void save(MultipartFile file);
}
