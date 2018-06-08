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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Gruppenauswahl extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gruppenauswahl);

        Button newGroup = (Button) findViewById(R.id.btnGrErstellen);
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
                        //String a=Account.getAccount().getId();
                        //Log.e("bababdi",a);
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
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
    }
}
