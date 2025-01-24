package com.example.file.processor.service;

import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

@Service
public class JsonFileProcessor implements FileProcessor {

    private final FileParser fileParser;
    private final FileConverter fileConverter;

    public JsonFileProcessor(FileParser fileParser, FileConverter fileConverter) {
        this.fileParser = fileParser;
        this.fileConverter = fileConverter;
    }

    @SneakyThrows
    @Override
    public Resource processFile(MultipartFile file) {
        var data = fileParser.convertContentsToPersonData(getContentsAsList(file));

        return fileConverter.convertToFile(data);
    }

    private Stream<String> getContentsAsList(MultipartFile file) throws IOException {
        return new BufferedReader(new InputStreamReader(file.getInputStream()))
                .lines();
    }
}
