package com.example.zioerjens.fitbet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GruppeDetail extends AppCompatActivity {

    private String gruppenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gruppe_detail);

        Intent intent = getIntent();

        gruppenName = intent.getStringExtra("gruppe");

        setTitle(gruppenName);
    }
}
