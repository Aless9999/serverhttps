package org.macnigor.serverhttps.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.time.Instant;

public class User {

    private String username;      // Уникальный логин
    private String passwordHash;  // Захешированный пароль
    @JsonProperty("apiKey")
    private String apiKey;        // Секретный токен для авторизации
    private String role;          // (опционально) роль или тип клиента
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant createdAt;   // Дата регистрации

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}

