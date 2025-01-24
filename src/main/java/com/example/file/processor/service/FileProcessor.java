package com.example.file.processor.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileProcessor {
    Resource processFile(MultipartFile file);
}
