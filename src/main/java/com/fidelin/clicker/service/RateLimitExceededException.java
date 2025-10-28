package com.fidelin.clicker.service;
public class RateLimitExceededException extends RuntimeException {
    public RateLimitExceededException(String msg) { super(msg); }
}
