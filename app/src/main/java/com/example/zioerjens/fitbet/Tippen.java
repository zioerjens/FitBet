package com.example.zioerjens.fitbet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class Tippen extends AppCompatActivity {

    private TextView land1Name;
    private TextView land1Icon;
    private TextView land2Name;
    private TextView land2Icon;
    private TextView homeScore;
    private TextView awayScore;
    private EditText tippHome;

    private EditText tippAway;
    private Button submitTipp;
    private String spielName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tippen);
        Intent intent = this.getIntent();
        spielName = intent.getStringExtra("spielName");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = this.getIntent();
        String land1NameStr = intent.getStringExtra("land1Name");
        String land1IconStr = intent.getStringExtra("land1Icon");
        String land2NameStr = intent.getStringExtra("land2Name");
        String land2IconStr = intent.getStringExtra("land2Icon");
        String homeScoreStr = intent.getStringExtra("homeScore");
        String awayScoreStr = intent.getStringExtra("awayScore");

        land1Name = (TextView) findViewById(R.id.land1Name);
        land1Icon = (TextView) findViewById(R.id.land1Icon);
        land2Name = (TextView) findViewById(R.id.land2Name);
        land2Icon = (TextView) findViewById(R.id.land2Icon);
        homeScore = (TextView) findViewById(R.id.homeScore);
        awayScore = (TextView) findViewById(R.id.awayScore);

        land1Name.setText(land1NameStr);
        land1Icon.setText(land1IconStr);
        land2Name.setText(land2NameStr);
        land2Icon.setText(land2IconStr);
        homeScore.setText(homeScoreStr);
        awayScore.setText(awayScoreStr);

        tippHome = findViewById(R.id.tippHome);
        tippAway = findViewById(R.id.tippAway);
        submitTipp = findViewById(R.id.submitTipp);

        submitTipp.setOnClickListener(new SubmitTippListener(this, spielName));
    }

    public EditText getTippHome() {
        return tippHome;
    }

    public EditText getTippAway() {
        return tippAway;
    }

}
