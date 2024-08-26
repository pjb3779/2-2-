package com.example.demo.model;

import java.time.LocalDateTime;

public class Bookshelf {
  
  public int UserID;
  
  public int DocumentID;
  public LocalDateTime UpdateTime;
  public LocalDateTime FavorTime;

  public int getId() {
      return UserID;
  }
 
  public void setId(int id) {
      this.UserID = id;
  }
 
  public void setDocumentID(int DocumentID) {
      this.DocumentID = DocumentID;
  }
  public int getDocumentID() {
      return DocumentID;
  }

  public LocalDateTime getUpdateTime() {
      return UpdateTime;
  }
  public LocalDateTime getFavorTime() {
      return FavorTime;
  }
  public void setUpdateTime(LocalDateTime UpdateTime) {
      this.UpdateTime = UpdateTime;
  }
  public void setFavorTime(LocalDateTime FavorTime) {
      this.FavorTime = FavorTime;
  }
  // Getters and Setters
}


