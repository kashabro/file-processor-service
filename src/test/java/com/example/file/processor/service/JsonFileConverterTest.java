package com.example.file.processor.service;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

import static com.example.file.processor.testSupport.PersonDataUtil.getPersonData;
import static com.example.file.processor.testSupport.ResourceUtil.readTestFile;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonFileConverterTest {

    @Test
    void given_personData_then_returnOutputFile() throws IOException {
        //Given
        var data = getPersonData();
        JsonFileConverter converter = new JsonFileConverter();
        var expected = readTestFile("OutputTest.json");

        //When
        Resource actual = converter.convertToFile(List.of(data));

        //Then
        assertEquals(expected.getContentAsString(UTF_8), actual.getContentAsString(UTF_8));

    }

}