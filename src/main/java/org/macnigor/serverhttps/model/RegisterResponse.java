package org.macnigor.serverhttps.model;




public class RegisterResponse {

    private String status;
    private String apiKey;
    private String message;

    public RegisterResponse(String status, String apiKey, String message) {
        this.status = status;
        this.apiKey = apiKey;
        this.message = message;


    }

    public RegisterResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
