package com.testdemo.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testdemo.demo.Service.NotebookService;
import com.testdemo.demo.response.ApiResponse;
import com.testdemo.demo.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/notes")
public class NotebookRestController {

    @Autowired
    private NotebookService notebookService;

    // 取得自己的筆記
    @GetMapping
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getMyNotes(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            String token = authHeader.substring(7);
            String account = JwtUtil.extractAccount(token);

            List<Map<String, Object>> notes = notebookService.getNotesVisibleTo(account);
            return ResponseEntity.ok(ApiResponse.success("查詢成功", notes));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error("查詢筆記失敗: " + e.getMessage()));
        }
    }

    // 新增筆記
    @PostMapping
    public ResponseEntity<ApiResponse<String>> addNote(HttpServletRequest request, @RequestBody Map<String, String> payload) {
        try {
            String authHeader = request.getHeader("Authorization");
            String token = authHeader.substring(7);
            String account = JwtUtil.extractAccount(token);

            String content = payload.get("content");
            String shared = payload.getOrDefault("shared", null);

            int rows = notebookService.addNote(account, content, shared);
            if (rows == 1) {
                return ResponseEntity.ok(ApiResponse.success("筆記已新增成功！", null));
            } else {
                return ResponseEntity.badRequest().body(ApiResponse.error("新增筆記失敗"));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error("新增筆記失敗: " + e.getMessage()));
        }
    }

    // 修改筆記
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateNote(@PathVariable int id, @RequestBody Map<String, String> payload) {
        try {
            String content = payload.get("content");
            String shared = payload.getOrDefault("shared", null);

            int rows = notebookService.updateNote(id, content, shared);
            if (rows == 1) {
                return ResponseEntity.ok(ApiResponse.success("筆記更新成功！", null));
            } else {
                return ResponseEntity.badRequest().body(ApiResponse.error("筆記更新失敗"));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error("筆記更新失敗: " + e.getMessage()));
        }
    }

    // 刪除筆記
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteNote(@PathVariable int id) {
        try {
            int rows = notebookService.deleteNote(id);
            if (rows == 1) {
                return ResponseEntity.ok(ApiResponse.success("筆記已刪除", null));
            } else {
                return ResponseEntity.badRequest().body(ApiResponse.error("筆記刪除失敗"));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error("筆記刪除失敗: " + e.getMessage()));
        }
    }
}

