package com.example.zioerjens.fitbet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;

//Dient dem Öffnen des Intents zum Tippen, wenn auf ein Spiel in der Liste geklickt wurde.
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

        //Überprüfen, ob die Teams für das Spiel bereits feststehen.
        if (selected.landHome != null && selected.landAway != null) {

            //Werte können nicht als Objekte Übertragen werden, deswegen werden sie hier alle einzeln Übertragen.
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
