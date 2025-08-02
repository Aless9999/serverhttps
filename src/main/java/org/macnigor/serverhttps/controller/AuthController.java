package org.macnigor.serverhttps.controller;

import jakarta.validation.Valid;
import org.macnigor.serverhttps.model.RegisterRequest;
import org.macnigor.serverhttps.model.RegisterResponse;
import org.macnigor.serverhttps.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/check")
    public ResponseEntity<String>  checkAuth(){
        var auth = SecurityContextHolder.getContext().getAuthentication();

        String user = (auth!=null) ? auth.getName():"anonymous";
        return ResponseEntity.ok("Authenticated as: " + user);

    }

    @GetMapping("/weathernow")
    public ResponseEntity<String> getWeatherNow() {
        try {
            String weatherInfo = userService.getWeatherNow();
            return ResponseEntity.ok(weatherInfo);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }
    }



    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        return userService.register(request.getUsername(), request.getPassword())
                .map(user -> new RegisterResponse("success", user.getApiKey(), null))
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new RegisterResponse("error", null, "User already exists")));
    }



}



