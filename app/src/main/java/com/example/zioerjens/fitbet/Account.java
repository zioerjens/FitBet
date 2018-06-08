package com.example.zioerjens.fitbet;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

public class Account {

    private static FirebaseUser instance;

    public static synchronized FirebaseUser getAccount(){
        return instance;
    }

    public static synchronized void setAccount(FirebaseUser account){
        instance = account;
    }
}
