package com.Authentication.System.AuthenticationSystem.controller;

import com.Authentication.System.AuthenticationSystem.dto.*;
import com.Authentication.System.AuthenticationSystem.enitity.type.Role;
import com.Authentication.System.AuthenticationSystem.repository.UserRepository;
import com.Authentication.System.AuthenticationSystem.security.JwtHelper;
import com.Authentication.System.AuthenticationSystem.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/test")
    public String test() {
        return "Server is running";
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String email = registerRequest.getEmail();
        Role role = registerRequest.getRole();

        UserDto userDto = authService.register(username, email, password, role);
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .data(userDto)
                .message("User Registered successfully")
                .status(201)
                .build();
        return ResponseEntity.status(201).body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        String token = authService.login(email, password); // username = email
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .data(token)
                .message("User login successfully")
                .status(200)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
