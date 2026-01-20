package org.macnigor.serverhttps.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record RegisterResponse(
        @JsonProperty("status") String status,
        @JsonProperty("apiKey") String apiKey,
        @JsonProperty("message") String message
) {
    @JsonCreator
    public RegisterResponse {
    }
}
