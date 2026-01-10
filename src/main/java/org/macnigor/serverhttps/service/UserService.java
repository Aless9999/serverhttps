package org.macnigor.serverhttps.service;

import jakarta.validation.Valid;
import org.macnigor.serverhttps.Result;
import org.macnigor.serverhttps.bot.weather.WeatherApp;
import org.macnigor.serverhttps.model.RegisterRequest;
import org.macnigor.serverhttps.model.RegisterResponse;
import org.macnigor.serverhttps.model.User;
import org.macnigor.serverhttps.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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


    public ResponseEntity<RegisterResponse> responseAfterRegister(@Valid RegisterRequest request) {
        Optional<User> createUser = register(request.getUsername(),request.getPassword());
        if(createUser.isPresent()){
            User user = createUser.get();
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setStatus("access");
            registerResponse.setApiKey(user.getApiKey());
            registerResponse.setMessage("Hello, "+ user.getUsername()+" ! You is register");
            return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new RegisterResponse("error", null, "User already exists"));
        }



    }
}


