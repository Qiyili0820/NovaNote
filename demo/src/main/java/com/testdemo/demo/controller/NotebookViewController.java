package com.testdemo.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.testdemo.demo.Service.NotebookService;

@Controller
public class NotebookViewController {

    @Autowired
    private NotebookService notebookService;

    // 顯示 HTML 筆記列表畫面（用 Thymeleaf）
    @GetMapping("/notes/view/{account}")
    public String viewNotes(@PathVariable String account, Model model) {
        List<Map<String, Object>> notes = notebookService.getNotesVisibleTo(account);
        model.addAttribute("notes", notes);
        return "notes-list"; // 這會對應 templates/notes-list.html
    }
}
