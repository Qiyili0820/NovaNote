package com.testdemo.demo.controller;

import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

    // 固定一個夠長的 SECRET key，這樣 server 重啟也一樣
    private static final String SECRET = "my-very-very-very-very-secret-key-which-is-long-enough-1234567890";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    // 產生 token
    public static String generateToken(String account) {
        return Jwts.builder()
                .setSubject(account)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000))  // 1小時有效
                .signWith(SECRET_KEY)
                .compact();
    }

    // 解析 token 取出 account
    public static String extractAccount(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 驗證 token 是否有效
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
