package com.example.wizaccountsystem.controller;

import com.example.wizaccountsystem.security.JwtUtil;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        // 範例簡單比對
        if ("admin".equals(username) && "password123".equals(password)) {
            String token = jwtUtil.generateToken(username);

            // 用 HttpOnly cookie 回傳
            ResponseCookie cookie = ResponseCookie.from("token", token)
                    .httpOnly(true)
                    .path("/")
                    .maxAge(60 * 60)    // 1 小時
                    .sameSite("Strict")
                    .build();

            return ResponseEntity
                    .ok()
                    .header("Set-Cookie", cookie.toString())
                    .body("登入成功");
        }

        return ResponseEntity.status(401).body("帳號或密碼錯誤");
    }
}

