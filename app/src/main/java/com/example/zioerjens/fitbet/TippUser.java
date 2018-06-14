package com.example.zioerjens.fitbet;

import com.google.firebase.auth.FirebaseUser;

public class TippUser {

    private Tipp tipp;
    private User user;

    public TippUser(Tipp tipp, User user) {
        this.tipp = tipp;
        this.user = user;
    }
}
