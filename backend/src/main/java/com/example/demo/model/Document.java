package com.example.demo.model;

import java.time.LocalDateTime;

public class Document {
  
  private int DocumentID;
  
  private String title;
  private String tags;
  private int AutherID;
  private String summary;
  private int CategoryID;
  private int Popularity;
  private LocalDateTime CurrentModifyTime; // 对应数据库中的 DATETIME 字段
  private LocalDateTime publishTime;
  private byte[] cover;
  private int chapterNum;

  
  public int getChapterNum() {
      return chapterNum;
  }
  public void setChapterNum(int chapterNum) {
      this.chapterNum = chapterNum;
  }
  public byte[] getCover() {
      return cover;
  }
  public void setCover(byte[] cover) {
      this.cover = cover;
  }
  
  public String getSummary() {
      return summary;
  }
  public String getTitle() {
      return title;
  }
  public void setSummary(String summary) {
      this.summary = summary;
  }
  public void setTitle(String title) {
      this.title = title;
  }

  public int getPopularity() {
      return Popularity;
  }
  public void setPopularity(int popularity) {
      Popularity = popularity;
  }

  
  public LocalDateTime getPublishTime() {
      return publishTime;
  }
  
  public void setPublishTime(LocalDateTime publishTime) {
      this.publishTime = publishTime;
  }

  public int getDocumentID() {
      return DocumentID;
  }
  public void setDocumentID(int documentID) {
      DocumentID = documentID;
  }

  public void setTags(String tags) {
      this.tags = tags;
  }
  public String getTags() {
      return tags;
  }

  public int getAutherID() {
      return AutherID;
  }
  public void setAutherID(int autherID) {
      AutherID = autherID;
  }
  public void setCategoryID(int categoryID) {
      this.CategoryID = categoryID;
  }
  public int getCategoryID() {
      return CategoryID;
  }
  public void setCurrentModifyTime(LocalDateTime currentModifyTime) {
      CurrentModifyTime = currentModifyTime;
  }
  public LocalDateTime getCurrentModifyTime() {
      return CurrentModifyTime;
  }
  
  // Getters and Setters
}


