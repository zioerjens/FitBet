package com.example.zioerjens.fitbet;

//Dient dem Einspeichern in die Datenbank
public class Gruppe_User {
    public Gruppe gruppe;
    public User user;

    public Gruppe_User(Gruppe gruppe,User user){
        this.gruppe = gruppe;
        this.user = user;
    }

    public Gruppe_User(){}
}
