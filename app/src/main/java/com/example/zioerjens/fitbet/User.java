package com.example.zioerjens.fitbet;

public class User {
    public String username;
    public String userID;
    public String userEmail;

    public User(String username, String userID, String userEmail) {
        this.username = username;
        this.userID = userID;
        this.userEmail = userEmail;
    }

    public User(){}

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userID='" + userID + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
