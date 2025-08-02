package org.macnigor.serverhttps.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterResponse {

    private String status;
    private String apiKey;
    private String message;

    public RegisterResponse(String status, String apiKey, String message) {
        this.status = status;
        this.apiKey = apiKey;
        this.message = message;
    }
}
