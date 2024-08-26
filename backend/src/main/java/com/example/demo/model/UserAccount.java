package com.example.demo.model;

public class UserAccount {

    private int userID;
    private String userName;
    private Integer age;
    private String passWords;
    private String salt;

    public int getId() {
        return userID;
    }

    public void setId(int id) {
        this.userID = id;
    }

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassWords() {
        return passWords;
    }
    public void setPassWords(String passWords){
        this.passWords = passWords;
    }

    public String getSalt(){
        return salt;
    }
    public void setSalt(String salt){
        this.salt = salt;
    }
}