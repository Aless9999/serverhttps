package org.macnigor.serverhttps.model;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
@Getter
@Setter
public class User {
    private String username;      // Уникальный логин
    private String passwordHash;  // Захешированный пароль
    private String apiKey;        // Секретный токен для авторизации
    private String role;          // (опционально) роль или тип клиента
    private Instant createdAt;    // Дата регистрации
}

