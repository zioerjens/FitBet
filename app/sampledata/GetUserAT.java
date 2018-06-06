package com.example.zioerjens.fitbet;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URL;
import java.util.Map;

public class GetUserAT extends AsyncTask<Void, Void, Void>
{
    private String username;
    private Boolean valid;
    private Register registerActivity;
    //TODO return von boolean bei diesem async task -> https://stackoverflow.com/questions/16752073/how-do-i-return-a-boolean-from-asynctask

    public GetUserAT(String username, Register registerActivity){
        this.username = username;
        this.registerActivity = registerActivity;
    }

    @Override
    protected Void doInBackground(Void... params) {

        Log.e("GetUserAt", "hier");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("user");
        registerActivity.setUsed(false);

        // Retrieve new posts as they are added to Firebase
        ref.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to Firebase
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                Map<String, Object> newUser = (Map<String, Object>) snapshot.getValue();
                String name = newUser.get("username").toString();
                System.out.print(name);
                Log.e("sven",name);
                if (name.equals(username)){
                    registerActivity.setUsed(true);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
            //... ChildEventListener also defines onChildChanged, onChildRemoved,
            //    onChildMoved and onCanceled, covered in later sections.

        });
        //registerActivity.setUsed(valid);
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        //super.onPostExecute(v);
    }
}
