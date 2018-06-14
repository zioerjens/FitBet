package com.example.zioerjens.fitbet;

import android.content.DialogInterface;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubmitTippListener implements View.OnClickListener {

    private Tippen activity;
    private String spielName;

    public SubmitTippListener(Tippen activity, String spielName){
        this.activity = activity;
        this.spielName = spielName;
    }

    @Override
    public void onClick(View v) {
        String tippHome = activity.getTippHome().getText().toString();
        String tippAway = activity.getTippAway().getText().toString();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("tipp_user");
        DatabaseReference ref2 = ref.push();
        ref2.setValue(new TippUser(new Tipp(tippHome,tippAway,spielName),new User(currentUser.getDisplayName(), currentUser.getUid(), currentUser.getEmail())));
    }
}
