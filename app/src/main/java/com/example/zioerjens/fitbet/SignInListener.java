package com.example.zioerjens.fitbet;

import android.view.View;

public class SignInListener implements View.OnClickListener{

    private SignIn signInActivity;

    public SignInListener(SignIn signInActivity){
        this.signInActivity = signInActivity;
    }

    @Override
    public void onClick(View v) {
        signInActivity.signIn();
    }
}
