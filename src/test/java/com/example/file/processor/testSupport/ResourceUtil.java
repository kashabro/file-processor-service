package com.example.file.processor.testSupport;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;


@UtilityClass
public class ResourceUtil {

    public static ClassPathResource readTestFile(String path) {
        return new ClassPathResource("testData/" + path);
    }

    @SneakyThrows
    public static MockMultipartFile getMockMultiPartFile() {
        return new MockMultipartFile("file",
                "EntryFile.txt",
                MediaType.TEXT_PLAIN_VALUE,
                readTestFile("EntryFile.txt").getContentAsByteArray());
    }
}
