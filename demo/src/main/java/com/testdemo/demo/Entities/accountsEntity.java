package com.testdemo.demo.Entities;

import org.springframework.stereotype.Component;

@Component
public class accountsEntity {

    private Integer id;
    private String account;
    private String password;
    private String name;
    private String email;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
       public String getEmail() {
        return email; // 👉 新增 Getter
    }
    public void setEmail(String email) {
        this.email = email; // 👉 新增 Setter
    }
}
