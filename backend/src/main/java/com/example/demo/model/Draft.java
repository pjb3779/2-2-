package com.example.demo.model;

import java.time.LocalDateTime;

/* 这个类用来表示数据空中的“草稿箱”一表 */
public class Draft {
    private String title;
    private String content;
    private int userID;
    private String currentModityTime; // 对应数据库中的 DATETIME 字段
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCurrentModityTime() {
        return currentModityTime;
    }
    public void setCurrentModityTime(String currentModityTime) {
        this.currentModityTime = currentModityTime;
    }
    
    public String getContent() {
        return content;
    }
    public String getTitle() {
        return title;
    }
    public int getUserID() {
        return userID;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
}
