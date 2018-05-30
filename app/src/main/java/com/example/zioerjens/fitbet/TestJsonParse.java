package com.example.zioerjens.fitbet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TestJsonParse extends AppCompatActivity {

    private String url = "https://raw.githubusercontent.com/lsv/fifa-worldcup-2018/master/data.json";
    private TestJsonParse testA = this;
    private ProgressDialog mDialog;

    private ArrayAdapter laenderListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_json_parse);

        mDialog = ProgressDialog.show(this, "Lade BadiInfos", "Funfact:\nDini Mueter isch fett!");

        JsonAsynch jsonAsynch = new JsonAsynch(url,testA,mDialog);
        jsonAsynch.execute(url);

        ListView laender = (ListView) findViewById(R.id.teamL);
        laenderListe = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        laender.setAdapter(laenderListe);
    }

    public ArrayAdapter getLaenderListe() {
        return laenderListe;
    }
}
