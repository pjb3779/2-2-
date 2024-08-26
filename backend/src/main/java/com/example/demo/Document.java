package com.example.demo;

import java.time.LocalDateTime;

public class Document {
  
  private int id;
  
  private String title;
  private String label;
  private String summary;
  private int category;
  private String fileName;
  private int popularity;
  private String fileContent;
  private LocalDateTime currentModifyTime; // 对应数据库中的 DATETIME 字段
  private LocalDateTime publishTime;
  private byte[] cover;

  public byte[] getCover() {
      return cover;
  }
  public void setCategory(int category) {
      this.category = category;
  }
  public void setCover(byte[] cover) {
      this.cover = cover;
  }
  
  public String getFileName() {
      return fileName;
  }
  public int getId() {
      return id;
  }
  public String getLabel() {
      return label;
  }
  public String getSummary() {
      return summary;
  }
  public String getTitle() {
      return title;
  }
  public void setFileName(String fileName) {
      this.fileName = fileName;
  }
  public void setId(int id) {
      this.id = id;
  }
  public void setLabel(String label) {
      this.label = label;
  }
  public void setSummary(String summary) {
      this.summary = summary;
  }
  public void setTitle(String title) {
      this.title = title;
  }

  public void setPopularity(int popularity) {
      this.popularity = popularity;
  }
  public int getPopularity() {
      return popularity;
  }

  public String getFileContent() {
      return fileContent;
  }
  
  public void setFileContent(String fileContent) {
      this.fileContent = fileContent;
  }
  public int getCategory() {
      return category;
  }
  public LocalDateTime getCurrentModifyTime() {
      return currentModifyTime;
  }
  public LocalDateTime getPublishTime() {
      return publishTime;
  }
  public void setCategoryID(int category) {
      this.category = category;
  }
  public void setCurrentModifyTime(LocalDateTime currentModifyTime) {
      this.currentModifyTime = currentModifyTime;
  }
  public void setPublishTime(LocalDateTime publishTime) {
      this.publishTime = publishTime;
  }
  // Getters and Setters
}


