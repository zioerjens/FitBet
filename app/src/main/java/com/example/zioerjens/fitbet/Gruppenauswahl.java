package com.example.zioerjens.fitbet;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Gruppenauswahl extends AppCompatActivity {

    DatabaseReference databaseGruppen;
    ArrayList<Gruppe> listgruppe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gruppenauswahl);

        databaseGruppen = FirebaseDatabase.getInstance().getReference().child("gruppen");

        listgruppe = new ArrayList<>();

        final Button newGroup = (Button) findViewById(R.id.btnGrErstellen);
        Button joinGroup = (Button) findViewById(R.id.btnGrBeitreten);
        newGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Gruppenauswahl.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_new_group,null);
                final EditText mName = (EditText) mView.findViewById(R.id.groupname);
                final EditText mPassword = (EditText) mView.findViewById(R.id.password);
                Button mNewGroupe = (Button) mView.findViewById(R.id.btnNewGr);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mNewGroupe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference("gruppe");
                        DatabaseReference usersRef = ref.push();

                        Gruppe g = new Gruppe(mName.getText().toString(),mPassword.getText().toString());
                        //usersRef.child("gruppe").setValue(g);
                        usersRef.setValue(g);

                        //Add current user to new Group
                        DatabaseReference ref2 = database.getReference("gruppe_user");
                        DatabaseReference usersRef2 = ref2.push();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String currentUserID = user.getUid();

                        Gruppe_User gu = new Gruppe_User(currentUserID,usersRef.getKey());
                        usersRef2.setValue(gu);

                        Log.e("CurrentUserId",currentUserID);



                        dialog.dismiss();




                    }
                });
            }
        });

        joinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Gruppenauswahl.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_join_group,null);
                final EditText mName = (EditText) mView.findViewById(R.id.groupnameJoin);
                final EditText mPassword = (EditText) mView.findViewById(R.id.passwordJoin);
                Button mjoinGr = (Button) mView.findViewById(R.id.btnGrBeitreten);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mjoinGr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(Gruppe g : listgruppe){
                            Log.w("Name:",g.name);
                            if(mName.getText().toString()==g.name){
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference ref = database.getReference("gruppe");
                                DatabaseReference usersRef = ref.child(g.name);


                                DatabaseReference ref2 = database.getReference("gruppe_user");
                                DatabaseReference usersRef2 = ref2.push();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String currentUserID = user.getUid();

                                Gruppe_User gu = new Gruppe_User(currentUserID,usersRef.getKey());
                                usersRef2.setValue(gu);
                                dialog.dismiss();
                            }
                        }
                    }
                });

            }
        });



    }
    @Override
    protected void onStart() {
        super.onStart();

        /*databaseGruppen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listgruppe.clear();

                for(DataSnapshot gruppeSnapshot : dataSnapshot.getChildren()){
                    Gruppe gruppe = gruppeSnapshot.getValue(Gruppe.class);
                    listgruppe.add(gruppe);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Gruppe g = dataSnapshot.getValue(Gruppe.class);
                listgruppe.add(g);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseGruppen.addValueEventListener(valueEventListener);
    }
}
