package com.pbs.controller;

import com.pbs.config.JwtTokenProvider;
import com.pbs.dto.AuthRequest;
import com.pbs.dto.AuthResponse;
import com.pbs.dto.UserDto;
import com.pbs.model.Role;
import com.pbs.model.User;
import com.pbs.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.Collections;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, 
                         JwtTokenProvider jwtTokenProvider, 
                         UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            User user = userService.getUserByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String token = jwtTokenProvider.createToken(
                    user.getEmail(),
                    user.getRoles().stream().map(Role::name).collect(Collectors.toList())
            );

            AuthResponse response = new AuthResponse(
                    token,
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getRoles()
            );

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email/password combination");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto) {
        if (userService.emailExists(userDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        // Default role is PLAYER if not specified
        if (userDto.getRoles() == null || userDto.getRoles().isEmpty()) {
            userDto.setRoles(Collections.singletonList(Role.ROLE_PLAYER));
        }

        User createdUser = userService.createUser(userDto);

        String token = jwtTokenProvider.createToken(
                createdUser.getEmail(),
                createdUser.getRoles().stream().map(Role::name).collect(Collectors.toList())
        );

        AuthResponse response = new AuthResponse(
                token,
                createdUser.getId(),
                createdUser.getName(),
                createdUser.getEmail(),
                createdUser.getRoles()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
