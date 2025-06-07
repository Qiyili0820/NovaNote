package com.testdemo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testdemo.demo.Entities.accountsEntity;
import com.testdemo.demo.Service.accountService;
import com.testdemo.demo.response.ApiResponse;



@RestController
public class DemoController {

	@Autowired
	accountService accountService;
	@Autowired
	accountsEntity accountEntity;
    @GetMapping("/hello")
 	public ResponseEntity<ApiResponse<String>> hello() {
        return ResponseEntity.ok(ApiResponse.success("成功", "Hey, Spring Boot 的 Hello World ! "));
    }
}
