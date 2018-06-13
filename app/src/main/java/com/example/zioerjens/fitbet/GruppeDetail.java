package com.example.zioerjens.fitbet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GruppeDetail extends AppCompatActivity {

    private String gruppenName;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gruppe_detail);

        Intent intent = getIntent();

        gruppenName = intent.getStringExtra("gruppe");

        setTitle(gruppenName);

        recyclerView = (RecyclerView) findViewById(R.id.listGroupMember);
    }


}
