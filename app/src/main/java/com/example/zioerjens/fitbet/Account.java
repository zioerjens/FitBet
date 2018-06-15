package com.example.zioerjens.fitbet;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

//Wurde bei dem ehemaligen Login-Verfahren verwendet.
public class    Account {

    private static FirebaseUser instance;

    public static synchronized FirebaseUser getAccount(){
        return instance;
    }

    public static synchronized void setAccount(FirebaseUser account){
        instance = account;
    }
}
