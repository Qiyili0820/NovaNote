package com.testdemo.demo.util;

import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

    // 固定一個夠長的 SECRET key，這樣 server 重啟也一樣
    private static final String SECRET = "my-very-very-very-very-secret-key-which-is-long-enough-1234567890";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    // Refresh token 用另一組 key（安全性更好）
    private static final String REFRESH_SECRET = "my-super-refresh-secret-key-which-is-long-enough-0987654321";
    private static final SecretKey REFRESH_SECRET_KEY = Keys.hmacShaKeyFor(REFRESH_SECRET.getBytes());

    // 產生 access token
    public static String generateToken(String account) {
        System.out.println("🔑 [JwtUtil] SECRET_KEY (generateToken) = " + new String(SECRET_KEY.getEncoded()));  // log
        return Jwts.builder()
                .setSubject(account)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000))  // 1小時有效
                .signWith(SECRET_KEY)
                .compact();
    }

    // 解析 access token 取出 account
    public static String extractAccount(String token) {
        System.out.println("🔑 [JwtUtil] SECRET_KEY (extractAccount) = " + new String(SECRET_KEY.getEncoded()));  // log
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 驗證 access token 是否有效
    public static boolean validateToken(String token) {
        try {
            System.out.println("🔑 [JwtUtil] SECRET_KEY (validateToken) = " + new String(SECRET_KEY.getEncoded()));  // log
            Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 產生 refresh token
    public static String generateRefreshToken(String account) {
        return Jwts.builder()
                .setSubject(account)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 3600_000))  // 7天有效
                .signWith(REFRESH_SECRET_KEY)
                .compact();
    }

    // 解析 refresh token 取出 account
    public static String extractAccountFromRefreshToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(REFRESH_SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
