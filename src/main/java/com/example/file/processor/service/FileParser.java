package com.example.file.processor.service;

import com.example.file.processor.model.PersonData;

import java.util.List;
import java.util.stream.Stream;

public interface FileParser {
     List<PersonData> convertContentsToPersonData(Stream<String> contents);
}
