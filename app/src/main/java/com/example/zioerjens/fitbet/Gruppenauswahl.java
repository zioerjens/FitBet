package com.example.zioerjens.fitbet;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Gruppenauswahl extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gruppenauswahl);

        Button newGroup = (Button) findViewById(R.id.btnGrErstellen);
        newGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Gruppenauswahl.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_new_group,null);
                EditText mName = (EditText) mView.findViewById(R.id.groupname);
                EditText mPassword = (EditText) mView.findViewById(R.id.password);
                Button mNewGroupe = (Button) mView.findViewById(R.id.btnNewGr);
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
    }
}
