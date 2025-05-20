package com.example.wizaccountsystem.security;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.*;        // 取代 javax.servlet.*
import jakarta.servlet.http.*;   // 取代 javax.servlet.http.*
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    ) throws ServletException, IOException {
        String path = req.getRequestURI();
        // 排除登入與公共資源
        if (path.startsWith("/auth") || path.startsWith("/error")) {
            chain.doFilter(req, res);
            return;
        }

        // 從 Cookie 取出 token
        String token = null;
        if (req.getCookies() != null) {
            for (Cookie c : req.getCookies()) {
                if ("token".equals(c.getName())) {
                    token = c.getValue();
                }
            }
        }

        try {
            String username = jwtUtil.validateTokenAndGetUsername(token);
            // 你可以把 username 存到 SecurityContext 或 request attribute
            req.setAttribute("username", username);
            chain.doFilter(req, res);
        } catch (Exception e) {
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            res.getWriter().write("尚未授權或 Token 過期");
        }
    }
}
