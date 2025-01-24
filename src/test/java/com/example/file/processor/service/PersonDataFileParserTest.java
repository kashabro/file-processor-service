package com.example.file.processor.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import static com.example.file.processor.testSupport.PersonDataUtil.getPersonData;
import static com.example.file.processor.testSupport.ResourceUtil.readTestFile;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonDataFileParserTest {

    private static final String TEST_FILENAME = "EntryFile.txt";
    private static final String INVALID_TEST_FILENAME = "InvalidEntryFile.txt";

    private static PersonDataFileParser parser;

    @BeforeAll
    static void setUp() {
        parser = new PersonDataFileParser();
    }

    @Test
    void when_fileContentsPassed_then_return_personDate() {
        //Given
        var contents = getStringStream(TEST_FILENAME);
        var expected = getPersonData();

        //When
        var actual = parser.convertContentsToPersonData(contents);

        //Then
        assertTrue(actual.contains(expected));
    }

    @Test
    void when_invalidFileContentsPassed_then_throwException() {
        //Given
        var contents = getStringStream(INVALID_TEST_FILENAME);

        //When
        assertThrows(RuntimeException.class, () -> parser.convertContentsToPersonData(contents));
    }

    @SneakyThrows
    private static Stream<String> getStringStream(String filename) {
        var file = readTestFile(filename);
        return new BufferedReader(new InputStreamReader(file.getInputStream())).lines();
    }

}