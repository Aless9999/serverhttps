package org.macnigor.serverhttps.controller;

import jakarta.validation.Valid;
import org.macnigor.serverhttps.model.RegisterRequest;
import org.macnigor.serverhttps.model.RegisterResponse;
import org.macnigor.serverhttps.service.util.ResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final ResponseService responseService;

    public AuthController(ResponseService responseService) {
        this.responseService = responseService;
    }


    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = responseService.responseAfterRegister(request);
        if (response.status().equals("access")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);


        }
    }


}



