package com.testdemo.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testdemo.demo.Entities.accountsEntity;
import com.testdemo.demo.Service.accountService;
import com.testdemo.demo.response.ApiResponse;
import com.testdemo.demo.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private accountService accountService;

    // ✅ 註冊 API
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody accountsEntity newAccount) {
        try {
            String result = accountService.register(newAccount);
            return ResponseEntity.ok(ApiResponse.success(result, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("註冊失敗: " + e.getMessage()));
        }
    }

    // ✅ 登入 API → 改版 → 回傳 refreshToken
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(@RequestBody accountsEntity loginData) {
        Optional<accountsEntity> existing = accountService.login(loginData.getAccount(), loginData.getPassword());
        if (existing.isPresent()) {
            String token = JwtUtil.generateToken(existing.get().getAccount());
            String refreshToken = JwtUtil.generateRefreshToken(existing.get().getAccount());

            Map<String, Object> data = new HashMap<>();
            data.put("account", existing.get().getAccount());
            data.put("token", token);
            data.put("refreshToken", refreshToken);
            data.put("expires_in", 3600);

            return ResponseEntity.ok(ApiResponse.success("登入成功", data));
        } else {
            return ResponseEntity.status(401).body(ApiResponse.error("帳號或密碼錯誤"));
        }
    }

    // ✅ 新增 Refresh Token API
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Map<String, Object>>> refreshAccessToken(@RequestBody Map<String, String> payload) {
        String refreshToken = payload.get("refreshToken");

        try {
            String account = JwtUtil.extractAccountFromRefreshToken(refreshToken);
            String newAccessToken = JwtUtil.generateToken(account);

            Map<String, Object> data = new HashMap<>();
            data.put("account", account);
            data.put("token", newAccessToken);
            data.put("expires_in", 3600);

            return ResponseEntity.ok(ApiResponse.success("刷新 token 成功", data));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Refresh token 無效或已過期"));
        }
    }

    // ✅ Token 驗證 API → 可以保留，不用改
    @GetMapping("/validate")
    public ResponseEntity<ApiResponse<String>> validateToken() {
        return ResponseEntity.ok(ApiResponse.success("Token 有效", null));
    }
}
