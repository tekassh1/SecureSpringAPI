package org.tekassh1.lab1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tekassh1.lab1.dto.JwtAuthResponse;
import org.tekassh1.lab1.dto.LogInRequest;
import org.tekassh1.lab1.dto.SignUpRequest;
import org.tekassh1.lab1.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public JwtAuthResponse login(@RequestBody LogInRequest logInRequest) {
        return authService.signIn(logInRequest.getUsername(), logInRequest.getPassword());
    }

    @PostMapping("/sign-up")
    public JwtAuthResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        return authService.signUp(signUpRequest.getUsername(), signUpRequest.getPassword());
    }
}
