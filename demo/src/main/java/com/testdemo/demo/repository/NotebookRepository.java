package com.testdemo.demo.repository;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NotebookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 查 userId (安全版)
    public Integer findUserIdByAccount(String account) {
        String sql = "SELECT id FROM accounts WHERE account = ?";
        List<Integer> userIds = jdbcTemplate.queryForList(sql, Integer.class, account);
        if (userIds.isEmpty()) {
            throw new RuntimeException("帳號不存在：" + account);
        }
        return userIds.get(0);
    }

    // 查詢某帳號可見的所有筆記 (改用 findUserIdByAccount + safe SQL)
    public List<Map<String, Object>> findAllVisibleNotes(String account) {
        Integer userId = findUserIdByAccount(account);  // 先查出 userId

        String sql = """
            SELECT * FROM notebooks
            WHERE userid = ?
               OR shared LIKE CONCAT('%', ?, '%')
        """;

        // 直接用 userId 查自己的筆記，shared 照原樣比對
        return jdbcTemplate.queryForList(sql, userId, account);
    }

    // 新增筆記
    public int insertNote(int userId, String content, String shared) {
        String insertSql = "INSERT INTO notebooks (userid, shared, content) VALUES (?, ?, ?)";
        return jdbcTemplate.update(insertSql, userId, shared, content);
    }

    // 更新筆記
    public int updateNote(int id, String content, String shared) {
        String sql = "UPDATE notebooks SET content = ?, shared = ? WHERE id = ?";
        return jdbcTemplate.update(sql, content, shared, id);
    }

    // 刪除筆記
    public int deleteNote(int id) {
        String sql = "DELETE FROM notebooks WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
