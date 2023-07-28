package com.example.mentorme;

public class Users {
    String name,email,password,expertise,userID;

    public Users(String name, String email, String password, String expertise, String userID) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.expertise = expertise;
        this.userID = userID;
    }
    public Users(){
        //empty constructor
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }
}
