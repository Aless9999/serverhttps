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

        Optional<User> user = userService.register(request.username(), request.password());

        if (user.isPresent()) {

            return new RegisterResponse("access",
                                        user.get().apikey(),
                               "You are registered");
        } else {
            return new RegisterResponse("error",
                                   null,
                                "User already exits");
        }
}}
