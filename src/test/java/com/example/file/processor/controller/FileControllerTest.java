package com.example.file.processor.controller;

import com.example.file.processor.validation.ValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static com.example.file.processor.testSupport.ResourceUtil.getMockMultiPartFile;
import static com.example.file.processor.testSupport.ResourceUtil.readTestFile;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FileControllerTest {

    @MockitoBean
    private ValidationService validationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void given_sendValidFile_then_return_Json() throws Exception {
        //Given
        var file = getMockMultiPartFile();
        var expected = readTestFile("OutcomeFile.json");

        //When
        when(validationService.checkValidation(any())).thenReturn(Optional.empty());

        //Then
        var actual = mockMvc.perform(MockMvcRequestBuilders.multipart("/files").file(file))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(expected.getContentAsString(UTF_8), actual.getResponse().getContentAsString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"China", "Spain", "USA"})
    void given_sendValidFileFromBlockedCountry_then_return_Forbidden(String country) throws Exception {
        //Given
        var file = getMockMultiPartFile();

        //When
        when(validationService.checkValidation(any())).thenReturn(Optional.of(country));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/files").file(file))
                .andExpect(status().is(403))
                .andExpect(content().string("Validation failed due to blocked origin: " + country));
    }

    @ParameterizedTest
    @ValueSource(strings = {"AWS", "GCP", "Azure"})
    void given_sendValidFileFromBlockedISP_then_return_Forbidden(String isp) throws Exception {
        //Given
        var file = getMockMultiPartFile();

        //When
        when(validationService.checkValidation(any())).thenReturn(Optional.of(isp));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/files").file(file))
                .andExpect(status().is(403))
                .andExpect(content().string("Validation failed due to blocked origin: " + isp));
    }

}