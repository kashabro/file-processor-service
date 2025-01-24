package com.example.file.processor.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PersonData(@JsonProperty("Name") String name,
                         @JsonProperty("Transport") String transport,
                         @JsonProperty("Top Speed") double topSpeed) {
}
