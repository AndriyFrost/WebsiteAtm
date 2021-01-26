package ua.lviv.frost.services;

import ua.lviv.frost.dto.JwtAuthenticationResponse;
import ua.lviv.frost.dto.LoginRequest;
import ua.lviv.frost.dto.UserRequest;
import ua.lviv.frost.dto.UserResponse;

public interface AuthService {

    UserResponse registerUser(UserRequest userRequest);

    JwtAuthenticationResponse loginUser(LoginRequest loginRequest);
}
