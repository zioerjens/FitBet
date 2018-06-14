package com.example.zioerjens.fitbet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TestJsonParse extends AppCompatActivity {

    private String url = "https://raw.githubusercontent.com/lsv/fifa-worldcup-2018/master/data.json";
    private TestJsonParse testA = this;
    private ProgressDialog mDialog;

    private ArrayAdapter laenderListe;
    private ArrayAdapter spieleListe;

    private ListView listView;
    private SpieleAdapter spieleAdapter;
    private RelativeLayout resultBar;

    private SportData data;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_json_parse);

        mDialog = ProgressDialog.show(this, "Lade BadiInfos", "Funfact:\nDini Mueter isch fett!");

        JsonAsynch jsonAsynch = new JsonAsynch(url,testA,mDialog);
        jsonAsynch.execute(url);

        Intent intent = getIntent();

        setTitle("Statistik von "+ intent.getStringExtra("userName"));

        laenderListe = new ArrayAdapter<Land>(this, android.R.layout.simple_list_item_1);
        spieleListe = new ArrayAdapter<Spiele>(this, android.R.layout.simple_list_item_1);


        //Spaghetti could be outsourced
        DatabaseReference statRef = FirebaseDatabase.getInstance().getReference("sportler");
        //String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference actualData = statRef.child(intent.getStringExtra("userID"));


        actualData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data = dataSnapshot.getValue(SportData.class);

                //spaghetti part 2
                TextView pm = findViewById(R.id.TVPointsMultiplikator);
                String text = "Multiplikator: "+data.multiplier+"x";
                pm.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });




    }




    public ArrayAdapter getLaenderListe() {
        return laenderListe;
    }
    public ArrayAdapter getSpieleListe() {
        return spieleListe;
    }

}
