package com.example.Tuan8.jwt;

import org.springframework.stereotype.Service;

public interface TokenBlacklist {
    void addToBlacklist(String token);
    boolean isBlacklisted(String token);
}
