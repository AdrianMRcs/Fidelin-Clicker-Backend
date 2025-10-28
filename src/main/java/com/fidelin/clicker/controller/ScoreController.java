package com.fidelin.clicker.controller;

import com.fidelin.clicker.dto.ScoreResponse;
import com.fidelin.clicker.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/score")
@CrossOrigin(origins = "*")
public class ScoreController {
    private final UserService users;
    public ScoreController(UserService users) { this.users = users; }

    @PostMapping("/add")
    public ScoreResponse add(Authentication auth) {
        var u = users.addPoints(auth.getName(), 1);
        return new ScoreResponse(u.getUsername(), u.getScore());
    }

    @GetMapping("/me")
    public ScoreResponse me(Authentication auth) {
        var u = users.getByUsername(auth.getName());
        return new ScoreResponse(u.getUsername(), u.getScore());
    }
}
