package com.fidelin.clicker.controller;

import com.fidelin.clicker.dto.*;
import com.fidelin.clicker.repo.UserRepository;
import com.fidelin.clicker.security.JwtUtil;
import com.fidelin.clicker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserService users;
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    public AuthController(UserService users, UserRepository repo, PasswordEncoder encoder, JwtUtil jwt) {
        this.users = users; this.repo = repo; this.encoder = encoder; this.jwt = jwt;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody RegisterRequest req) {
        users.register(req.username(), req.password());
        String token = jwt.generate(req.username());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        var user = repo.findByUsername(req.username()).orElse(null);
        if (user == null || !encoder.matches(req.password(), user.getPasswordHash()))
            return ResponseEntity.status(401).body("Invalid credentials");
        return ResponseEntity.ok(new TokenResponse(jwt.generate(req.username())));
    }
}
