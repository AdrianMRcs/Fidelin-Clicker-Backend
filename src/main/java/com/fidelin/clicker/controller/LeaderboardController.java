package com.fidelin.clicker.controller;

import com.fidelin.clicker.model.User;
import com.fidelin.clicker.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/leaderboard")
@CrossOrigin(origins = "*")
public class LeaderboardController {
    private final UserService users;
    public LeaderboardController(UserService users){ this.users = users; }

    @GetMapping("/top")
    public List<User> top(@RequestParam(defaultValue = "50") int limit){
        if (limit < 1) limit = 1;
        if (limit > 200) limit = 200;
        return users.top(limit);
    }
}
