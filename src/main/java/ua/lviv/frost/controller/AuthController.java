package ua.lviv.frost.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.lviv.frost.dto.JwtAuthenticationResponse;
import ua.lviv.frost.dto.LoginRequest;
import ua.lviv.frost.dto.UserRequest;
import ua.lviv.frost.dto.UserResponse;
import ua.lviv.frost.services.implementation.AuthServiceImpl;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/register")
    public HttpEntity<UserResponse> register(@RequestBody @Valid UserRequest userRequest) {
        return new ResponseEntity<>(authServiceImpl.registerUser(userRequest), HttpStatus.OK);
    }

    @PostMapping("/login")
    public HttpEntity<JwtAuthenticationResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return new ResponseEntity<>(authServiceImpl.loginUser(loginRequest), HttpStatus.OK);
    }
}
