package com.fidelin.clicker.service;

import com.fidelin.clicker.model.User;
import com.fidelin.clicker.repo.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final RateLimiter rateLimiter;

    public UserService(UserRepository repo, PasswordEncoder encoder, RateLimiter rateLimiter) {
        this.repo = repo; this.encoder = encoder; this.rateLimiter = rateLimiter;
    }

    public User register(String username, String rawPassword) {
        if (repo.existsByUsername(username)) throw new RuntimeException("Username already taken");
        User u = new User();
        u.setUsername(username);
        u.setPasswordHash(encoder.encode(rawPassword));
        u.setScore(0);
        return repo.save(u);
    }

    public User getByUsername(String username) {
        return repo.findByUsername(username).orElseThrow();
    }

    public User addPoints(String username, long delta) {
        rateLimiter.check(username);
        User u = getByUsername(username);
        u.setScore(u.getScore() + delta);
        return repo.save(u);
    }

    public List<User> top(int n) {
        return repo.findAll(PageRequest.of(0, n, Sort.by(Sort.Direction.DESC, "score"))).getContent();
    }
}
