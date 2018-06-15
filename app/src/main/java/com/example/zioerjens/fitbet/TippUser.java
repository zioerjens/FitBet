package com.example.zioerjens.fitbet;

import com.google.firebase.auth.FirebaseUser;

//Klasse, die für die Speicherung in die Datenbank verwendet wird.

public class TippUser {

    public Tipp tipp;
    public User user;

    public TippUser(){}

    public TippUser(Tipp tipp, User user) {
        this.tipp = tipp;
        this.user = user;
    }
}
