package com.example.file.processor.service;

import com.example.file.processor.model.PersonData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class PersonDataFileParser implements FileParser {
    @Override
    public List<PersonData> convertContentsToPersonData(Stream<String> contents) {

        return contents
                .filter(content -> content.contains("|"))
                .map(c -> c.split("[|]"))
                .map(list -> new PersonData(list[2], list[4], Double.parseDouble(list[6])))
                .toList();
    }
}
