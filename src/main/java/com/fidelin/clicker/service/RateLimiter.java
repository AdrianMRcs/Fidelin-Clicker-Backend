package com.fidelin.clicker.service;

import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimiter {
    private static final int MAX_PER_SECOND = 5;
    private static final int MAX_PER_MINUTE = 200;
    private final Map<String, Deque<Long>> perUserTimestamps = new ConcurrentHashMap<>();

    public void check(String username) {
        long now = Instant.now().toEpochMilli();
        Deque<Long> q = perUserTimestamps.computeIfAbsent(username, k -> new ArrayDeque<>());
        long oneMinuteAgo = now - 60_000;
        while (!q.isEmpty() && q.peekFirst() < oneMinuteAgo) q.pollFirst();
        long oneSecondAgo = now - 1_000;
        int perSecond = 0;
        for (Long t : q) if (t >= oneSecondAgo) perSecond++;
        if (perSecond >= MAX_PER_SECOND) throw new RateLimitExceededException("Too many clicks per second");
        if (q.size() >= MAX_PER_MINUTE) throw new RateLimitExceededException("Too many clicks per minute");
        q.addLast(now);
    }
}
