package com.example.file.processor.service;

import com.example.file.processor.model.PersonData;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileConverter {
    Resource convertToFile(List<PersonData> personData) throws IOException;
}
