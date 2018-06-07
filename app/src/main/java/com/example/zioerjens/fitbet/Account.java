package com.example.zioerjens.fitbet;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class Account {

    private static GoogleSignInAccount instance;

    public static synchronized GoogleSignInAccount getAccount(){
        return instance;
    }

    public static synchronized void setAccount(GoogleSignInAccount account){
        instance = account;
    }
}
