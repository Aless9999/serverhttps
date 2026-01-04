package org.macnigor.serverhttps.config;

import org.macnigor.serverhttps.security.ApiKeyAuthFilter;
import org.macnigor.serverhttps.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    // Бин для выполнения HTTP-запросов к OpenWeather
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Бин фильтра авторизации (передаем в него репозиторий для проверки ключей в БД)
    @Bean
    public ApiKeyAuthFilter apiKeyAuthFilter(UserRepository userRepository) {
        return new ApiKeyAuthFilter(userRepository);
    }
}