package com.example.demo.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Comment {
    public int CommentID;
    public int UserID;
    public String Content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime PublishTime;
    
    public int DocumentID;
    public int likes;
    public int chapterNum;
    public String userName;
    // Getters

    public int getCommentID() {
        return CommentID;
    }
    
    public int getUserID() {
        return UserID;
    }

    public String getContent() {
        return Content;
    }

    public LocalDateTime getPublishTime() {
        return PublishTime;
    }

    public int getDocumentID(){
        return DocumentID;
    }

    public int getLikes(){
        return likes;
    }

    // Setters
    public void setCommentID(int CommentID) {
        this.CommentID = CommentID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public void setPublishTime(LocalDateTime PublishTime) {
        this.PublishTime = PublishTime;
    }

    public void setDocumentID(int DocumentID){
        this.DocumentID = DocumentID;
    }

    public void setLikes(int likes){
        this.likes = likes;
    }

}
