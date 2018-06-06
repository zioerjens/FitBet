package com.example.zioerjens.fitbet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class Register extends AppCompatActivity {

    private Boolean used;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Button register = (Button) findViewById(R.id.register);

        //Adding Google thing to it
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }

    private void addUserToDb(String username, String password){
        //TODO
    }
}
