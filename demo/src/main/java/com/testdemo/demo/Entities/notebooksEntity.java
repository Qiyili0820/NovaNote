package com.testdemo.demo.Entities;

import org.springframework.stereotype.Component;

@Component
public class notebooksEntity {

    private Integer id;
    private Integer userid;
    private String shared;
    private String content;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getShared() {
        return shared;
    }
    public void setShared(String shared) {
        this.shared = shared;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
