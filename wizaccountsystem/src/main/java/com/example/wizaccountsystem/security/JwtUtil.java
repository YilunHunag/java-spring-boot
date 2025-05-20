package com.example.wizaccountsystem.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private Key key;
    // 1 小時過期
    private long expirationMillis = 60 * 60 * 1000;

    @PostConstruct
    public void init() {
        // 隨機金鑰（實務可從環境變數或 keystore 載入）
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    // 產生 JWT
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(key)
                .compact();
    }

    // 解析並驗證 JWT，回傳 subject（username）
    public String validateTokenAndGetUsername(String token) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return claims.getBody().getSubject();
    }
}
