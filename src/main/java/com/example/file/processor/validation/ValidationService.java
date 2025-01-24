package com.example.file.processor.validation;

import java.util.Optional;

public interface ValidationService {
    Optional<String> checkValidation(String ip);
}
