package com.example.file.processor.validation;

import com.example.file.processor.model.Validation;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest()
@EnableWireMock({
        @ConfigureWireMock(
                port = 6070
        )
})
class ValidationServiceImplTest {

    private static final String WIREMOCK_URL = "http://localhost:6070/json/";

    private String testIp = "123.123.123";

    @Autowired
    private RestTemplate restTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    private ValidationServiceImpl validationService;

    @BeforeEach
    void setUp() {
        validationService = new ValidationServiceImpl(restTemplate, WIREMOCK_URL);
    }

    @SneakyThrows
    @Test
    void when_validIp_return_empty() {
        //Given
        stubFor(get(urlPathEqualTo("/json/" + testIp))
                .willReturn(ok(objectMapper.writeValueAsString(new Validation("Japan", "BT")))
                        .withHeader("Content-Type", "application/json")));

        //When
        var actual = validationService.checkValidation(testIp);

        //Then
        assertTrue(actual.isEmpty());
    }

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"China", "Spain", "USA"})
    void when_invalidIp_return_blockedCountry(String country) {
        //Given
        stubFor(get(urlPathEqualTo("/json/" + testIp))
                .willReturn(ok(objectMapper.writeValueAsString(new Validation(country, "aws")))
                        .withHeader("Content-Type", "application/json")));

        //When
        var actual = validationService.checkValidation(testIp);

        //Then
        assertEquals(country, actual.get());
    }

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"AWS", "GCP", "Azure"})
    void when_invalidIp_return_blockedISP(String isp) {
        //Given
        stubFor(get(urlPathEqualTo("/json/" + testIp))
                .willReturn(ok(objectMapper.writeValueAsString(new Validation("Japan", isp)))
                        .withHeader("Content-Type", "application/json")));

        //When
        var actual = validationService.checkValidation(testIp);

        //Then
        assertEquals(isp, actual.get());
    }
}