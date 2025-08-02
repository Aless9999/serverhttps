package org.macnigor.serverhttps;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "Username не должен быть пустым")
    @Size(min = 3, max = 20, message = "Username должен содержать от 3 до 20 символов")
    private String username;

    @NotBlank(message = "Password не должен быть пустым")
    @Size(min = 6, message = "Password должен содержать минимум 6 символов")
    private String password;

    // Геттеры и сеттеры
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}


