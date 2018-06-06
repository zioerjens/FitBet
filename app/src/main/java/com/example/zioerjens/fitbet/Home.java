package com.example.zioerjens.fitbet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        final Button buttonStat = findViewById(R.id.btnStatistik);
        buttonStat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TestJsonParse.class);
                startActivity(intent);
            }
        });

        final Button buttonTippen = findViewById(R.id.btnTippen);
        buttonTippen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),tippen_Spielauswahl.class);
                startActivity(intent);
            }
        });

        final Button buttonGruppen = findViewById(R.id.btnGruppen);
        buttonGruppen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Gruppenauswahl.class);
                startActivity(intent);
            }
        });

    }
}
