package com.example.file.processor.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static com.example.file.processor.testSupport.ResourceUtil.getMockMultiPartFile;
import static com.example.file.processor.testSupport.ResourceUtil.readTestFile;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JsonFileProcessorTest {

    @Autowired
    private JsonFileProcessor jsonFileProcessor;

    @Test
    void given_multiPartFile_then_returnResource() throws IOException {
        //Given
        var file = getMockMultiPartFile();
        var expected = readTestFile("OutcomeFile.json");

        //When
        var actual = jsonFileProcessor.processFile(file);

        //Then
        assertEquals(expected.getContentAsString(UTF_8), actual.getContentAsString(UTF_8));
    }

}