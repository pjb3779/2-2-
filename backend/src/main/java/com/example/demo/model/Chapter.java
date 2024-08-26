package com.example.demo.model;

import java.time.LocalDate;

public class Chapter {
    public int Num;
    public LocalDate creatTime;
    public String content;
    public String title;
    public int DocumentID;
    
    public int getDocumentID() {
        return this.DocumentID;
    }
    public void setDocumentID(int DocumentID) {
        this.DocumentID = DocumentID;
    }
    public String getContent() {
        return content;
    }
    public LocalDate getCreatTime() {
        return creatTime;
    }
    public int getNum() {
        return this.Num;
    }
    public String getTitle() {
        return this.title;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setCreatTime(LocalDate creatTime) {
        this.creatTime = creatTime;
    }
    public void setNum(int Num) {
        this.Num = Num;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
