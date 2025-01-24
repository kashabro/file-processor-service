package com.example.file.processor.validation;

import com.example.file.processor.model.Validation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Component
public class ValidationServiceImpl implements ValidationService {

    private static final List<String> BLOCKED_COUNTRIES = List.of("China", "Spain", "USA");
    private static final List<String> BLOCKED_ISP = List.of("AWS", "GCP", "Azure");

    private final RestTemplate restTemplate;

    String apiURI;

    public ValidationServiceImpl(RestTemplate restTemplate, @Value("${API_URI}") String apiURI) {
        this.restTemplate = restTemplate;
        this.apiURI = apiURI;
    }

    @Override
    public Optional<String> checkValidation(String ip) {

        var validation = restTemplate.getForObject(apiURI + ip, Validation.class);

        if (BLOCKED_COUNTRIES.contains(validation.country()))
            return Optional.of(validation.country());

        if (BLOCKED_ISP.contains(validation.isp()))
            return Optional.of(validation.isp());

        return Optional.empty();
    }
}
