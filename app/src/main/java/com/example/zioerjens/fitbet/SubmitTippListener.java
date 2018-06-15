package com.example.zioerjens.fitbet;

import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//Wird ausgeführt, wenn auf ein Spiel getippt wird.
public class SubmitTippListener implements View.OnClickListener {

    private Tippen activity;
    private String spielName;
    private String tippHome;
    private String tippAway;

    public SubmitTippListener(Tippen activity, String spielName){
        this.activity = activity;
        this.spielName = spielName;
    }

    @Override
    public void onClick(View v) {

        tippHome = activity.getTippHome().getText().toString();
        tippAway = activity.getTippAway().getText().toString();

        if (tippHome.equals("") || tippAway.equals("")){
            Toast.makeText(activity,R.string.fillBothTippFields,Toast.LENGTH_SHORT).show();
        } else {
            deleteTippIfExisting(FirebaseAuth.getInstance().getCurrentUser().getUid(), spielName);
        }
    }

    private void deleteTippIfExisting(final String uid, final String spielName){

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("tipp_user");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot grSnapshot : dataSnapshot.getChildren()) {
                    // Get Post object and use the values to update the UI
                    TippUser post = grSnapshot.getValue(TippUser.class);

                    if (post.user.userID.equals(uid) && post.tipp.spielName.equals(spielName)){
                        ref.child(grSnapshot.getKey()).removeValue();
                        Toast.makeText(activity,R.string.updatedTipp, Toast.LENGTH_SHORT).show();
                    }
                }
                addTipp();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void addTipp(){
        //Speichert den Tipp in die FirebaseDatabase
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("tipp_user");
        DatabaseReference ref2 = ref.push();
        ref2.setValue(new TippUser(new Tipp(tippHome,tippAway,spielName),new User(currentUser.getDisplayName(), currentUser.getUid(), currentUser.getEmail())));
        Toast.makeText(activity,R.string.successfulTipp,Toast.LENGTH_SHORT).show();
    }
}
