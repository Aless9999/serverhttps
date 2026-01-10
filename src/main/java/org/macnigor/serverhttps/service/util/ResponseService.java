package org.macnigor.serverhttps.service.util;

import jakarta.validation.Valid;
import org.macnigor.serverhttps.model.RegisterRequest;
import org.macnigor.serverhttps.model.RegisterResponse;
import org.macnigor.serverhttps.model.User;
import org.macnigor.serverhttps.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResponseService {
    private final UserService userService;

    public ResponseService(UserService userService) {
        this.userService = userService;
    }


    public RegisterResponse responseAfterRegister(@Valid RegisterRequest request) {
        RegisterResponse registerResponse = new RegisterResponse();

        // Регистрация пользователя с возвращаемым Optional
        Optional<User> user = userService.register(request.getUsername(), request.getPassword());

        // Если регистрация успешна
        if (user.isPresent()) {
            User registeredUser = user.get();
            registerResponse.setStatus("access");
            registerResponse.setApiKey(registeredUser.getApiKey());
            registerResponse.setMessage("You are registered");
            return registerResponse;
        } else {
            // Если пользователь уже существует
            registerResponse.setStatus("error");
            registerResponse.setApiKey(null);
            registerResponse.setMessage("User already exists");
            return registerResponse;
        }
}}
