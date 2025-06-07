package com.testdemo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.testdemo.demo.Entities.accountsEntity;
import com.testdemo.demo.Service.accountService;
import com.testdemo.demo.response.ApiResponse;

@RestController
@RequestMapping("/accounts")
public class AccountRestController {

    @Autowired
    private accountService accountService;

    // ✅ 註冊帳號：POST /accounts
    @PostMapping
    public ResponseEntity<ApiResponse<String>> register(@RequestBody accountsEntity newAccount) {
        try {
            String result = accountService.register(newAccount);
            if ("帳號已存在！".equals(result)) {
                return ResponseEntity.badRequest().body(ApiResponse.error("帳號已存在"));
            }
            return ResponseEntity.ok(ApiResponse.success("註冊成功", null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error("伺服器錯誤: " + e.getMessage()));
        }
    }

    // ✅ 查詢帳號：GET /accounts/{account}
    @GetMapping("/{account}")
    public ResponseEntity<ApiResponse<accountsEntity>> getAccount(@PathVariable String account) {
        try {
            accountsEntity acc = accountService.getAccount(account);
            if (acc == null) {
                return ResponseEntity.badRequest().body(ApiResponse.error("帳號不存在"));
            }
            return ResponseEntity.ok(ApiResponse.success("查詢成功", acc));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error("伺服器錯誤: " + e.getMessage()));
        }
    }

    // ✅ 修改帳號：PUT /accounts/{account}
    @PutMapping("/{account}")
    public ResponseEntity<ApiResponse<String>> updateAccount(@PathVariable String account, @RequestBody accountsEntity updateData) {
        try {
            String result = accountService.updateAccount(account, updateData);
            if ("帳號不存在".equals(result)) {
                return ResponseEntity.badRequest().body(ApiResponse.error(result));
            }
            return ResponseEntity.ok(ApiResponse.success("修改成功", null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error("伺服器錯誤: " + e.getMessage()));
        }
    }

    // ✅ 刪除帳號：DELETE /accounts/{account}
    @DeleteMapping("/{account}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable String account) {
        try {
            String result = accountService.deleteAccount(account);
            if ("帳號不存在".equals(result)) {
                return ResponseEntity.badRequest().body(ApiResponse.error(result));
            }
            return ResponseEntity.ok(ApiResponse.success("刪除成功", null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error("伺服器錯誤: " + e.getMessage()));
        }
    }
}

