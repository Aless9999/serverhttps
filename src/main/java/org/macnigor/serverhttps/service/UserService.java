package org.macnigor.serverhttps.service;

import org.macnigor.serverhttps.bot.weather.WeatherApp;
import org.macnigor.serverhttps.model.User;
import org.macnigor.serverhttps.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final WeatherApp weatherApp;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, WeatherApp weatherApp) {
        this.userRepository = userRepository;
        this.weatherApp = weatherApp;
    }

    public Optional<User> register(String username, String rawPassword) {
        if (userRepository.findByUsername(username).isPresent()) {
            return Optional.empty(); // Пользователь уже существует
        }

        String hashedPassword = passwordEncoder.encode(rawPassword);
        String apiKey = UUID.randomUUID().toString();

        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(hashedPassword);
        user.setApiKey(apiKey);
        user.setRole("user");
        user.setCreatedAt(Instant.now());

        userRepository.save(user);
        return Optional.of(user);
    }


    public String getWeatherNow() {

            String weatherInfo = weatherApp.getWeatherNow();// ...получаем данные о погоде...

            if (weatherInfo == null || weatherInfo.isBlank()) {
                throw new RuntimeException("Ошибка при получении погоды.");
            }

            return weatherInfo;
        }




    }


