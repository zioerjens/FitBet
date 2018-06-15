package com.example.zioerjens.fitbet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;

public class OnSpielClickListener implements AdapterView.OnItemClickListener {

    private TippenAllGames activity;
    private ArrayList<Land> alleLaender;

    public OnSpielClickListener(TippenAllGames activity, ArrayList<Land> alleLaender){
        this.activity = activity;
        this.alleLaender = alleLaender;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Spiele selected = (Spiele) parent.getItemAtPosition(position);
        if (selected.landHome != null && selected.landAway != null) {

            Intent intent = new Intent(activity, Tippen.class);
            intent.putExtra("land1Name", selected.landHome.getLandName());
            intent.putExtra("land1Icon", selected.landHome.getEmojiString());
            intent.putExtra("land2Name", selected.landAway.getLandName());
            intent.putExtra("land2Icon", selected.landAway.getEmojiString());
            intent.putExtra("homeScore", selected.homeResult);
            intent.putExtra("awayScore", selected.awayResult);
            intent.putExtra("spielName", selected.spielName);

            activity.startActivity(intent);
        } else {

            Toast.makeText(activity,R.string.gameNotReady,Toast.LENGTH_SHORT).show();
        }
    }
}
