package org.macnigor.serverhttps.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public record User(
        @JsonProperty("username") String username,
        @JsonProperty("password") String password, // Обычно тут хранится ХЕШ
        @JsonProperty("apikey") String apikey
) {
    @JsonCreator
    public User {
    }
}
