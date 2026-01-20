package org.macnigor.serverhttps.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public record RegisterRequest(
        @JsonProperty("username") String username,
        @JsonProperty("password") String password
) {
    @JsonCreator
    public RegisterRequest {
    }
}
