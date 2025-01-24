package com.example.file.processor.service;

import com.example.file.processor.model.PersonData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JsonFileConverter implements FileConverter {

    @SneakyThrows
    @Override
    public Resource convertToFile(List<PersonData> personData) {

        return new ByteArrayResource(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsBytes(personData));
    }
}
