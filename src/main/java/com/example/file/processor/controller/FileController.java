package com.example.file.processor.controller;

import com.example.file.processor.service.FileProcessor;
import com.example.file.processor.validation.ValidationService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping("/files")
public class FileController {

    private static final String OUTPUT_FILENAME = "OutcomeFile.json";

    private final ValidationService validationService;
    private final FileProcessor fileProcessor;

    public FileController(ValidationService validationService, FileProcessor fileProcessor) {
        this.validationService = validationService;
        this.fileProcessor = fileProcessor;
    }


    @PostMapping
    public ResponseEntity<Object> handleFileProcessing(MultipartHttpServletRequest request) {

        var validation = validationService.checkValidation(request.getRemoteAddr());

        if (validation.isPresent())
            return new ResponseEntity<>("Validation failed due to blocked origin: " + validation.get(), HttpStatus.FORBIDDEN);

        HttpHeaders header = getHttpHeaders();

        Resource resource = fileProcessor.processFile(request.getFile("file"));

        return ResponseEntity.ok()
                .headers(header)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + OUTPUT_FILENAME);
        return header;
    }
}
