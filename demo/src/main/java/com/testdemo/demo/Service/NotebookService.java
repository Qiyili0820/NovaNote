package com.testdemo.demo.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testdemo.demo.repository.NotebookRepository;

@Service
public class NotebookService {

    @Autowired
    private NotebookRepository notebookRepository;

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
}
