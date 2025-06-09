package com.testdemo.demo.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.testdemo.demo.repository.NotebookRepository;

@Service
public class NotebookService {

    @Autowired
    private NotebookRepository notebookRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;  // 加入 JdbcTemplate 注入

    // 查詢帳號可見的筆記
    public List<Map<String, Object>> getNotesVisibleTo(String account) {
        return notebookRepository.findAllVisibleNotes(account);
    }

    // 新增筆記
    public int addNote(String account, String content, String shared) {
        // 先查 userId
        Integer userId = notebookRepository.findUserIdByAccount(account);

        // 呼叫 insertNote
        return notebookRepository.insertNote(userId, content, shared);
    }

    // 更新筆記
    public int updateNote(int noteId, String content, String shared) {
        return notebookRepository.updateNote(noteId, content, shared);
    }

    // 刪除筆記
    public int deleteNote(int noteId) {
        return notebookRepository.deleteNote(noteId);
    }

    // 共享筆記給特定 userIds
    public int shareNote(int noteId, List<Integer> userIds) {
        // 先刪除舊的共享紀錄
        jdbcTemplate.update("DELETE FROM shared_notes WHERE note_id = ?", noteId);

        // 新增新的共享紀錄
        for (Integer userId : userIds) {
            jdbcTemplate.update("INSERT INTO shared_notes (note_id, shared_to_userid) VALUES (?, ?)", noteId, userId);
        }
        return userIds.size();
    }

    // 取得共享給自己的筆記
    public List<Map<String, Object>> getSharedNotes(String account) {
        String sql = """
            SELECT n.id, n.userid, n.shared, n.content
            FROM shared_notes s
            JOIN notebooks n ON s.note_id = n.id
            JOIN accounts a ON s.shared_to_userid = a.id
            WHERE a.account = ?
        """;

        return jdbcTemplate.queryForList(sql, account);
    }

    // 取消共享某 user
    public int unshareNote(int noteId, int userId) {
        return jdbcTemplate.update("DELETE FROM shared_notes WHERE note_id = ? AND shared_to_userid = ?", noteId, userId);
    }
}
