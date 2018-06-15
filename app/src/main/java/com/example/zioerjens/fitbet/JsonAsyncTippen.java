package com.example.zioerjens.fitbet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonAsyncTippen extends AsyncTask<String,String,String> {

    private TippenAllGames teamAct;
    private String url2;
    private ProgressDialog mDialog;
    private ArrayAdapter laenderListe;
    private ArrayList<Land> alleLaender;
    private ArrayAdapter spieleListe;

    private String landName;
    private String landId;
    private String fifaCode;
    private String flag;
    private String emoji;
    private String emojiString;
    private String iso2;


    private String spielName;
    private String spielType;
    private String homeTeam;
    private String awayTeam;
    private String homeResult;
    private String awayResult;
    private String spielDate;
    private String stadium;
    private String finished;
    private String matchday;
    private String winner;
    private String homePenalty;
    private String awayPenalty;

    private ListView listView;
    private SpieleAdapter spieleAdapter;


    private Spiele spiel;
    private Land land;

    public JsonAsyncTippen(String url, TippenAllGames teamAct, ProgressDialog mDialog){
        this.url2 = url;
        this.teamAct = teamAct;
        this.mDialog = mDialog;
    }

    //Holt das JSON im Hintergrund
    @Override
    protected String doInBackground(String... badi) {
        StringBuilder msg = new StringBuilder();

        HttpURLConnection urlConnection;
        try {
            URL url = new URL(badi[0]);

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;

            while ((line = reader.readLine()) != null) {
                msg.append(line);
            }
            mDialog.dismiss();


        } catch (Exception e) {
            Log.v("Fehler", e.toString());
            //throw e.getMessage();
        }
        return msg.toString();
    }

    //Wenn das JSON vorhanden ist, wird es verarbeitet und die Listener werden hinzugefügt
    protected void onPostExecute(String result) {

        alleLaender = new ArrayList<>();
        parseJsonLoadLand(result);
        parseJsonSpiel(result);
        listView = (ListView) teamAct.findViewById(R.id.spielL);
        listView.setOnItemClickListener(new OnSpielClickListener(teamAct,alleLaender));
    }

    //Liest die Länder aus dem JSON und speichert diese als Land-Objekt Arryadapter
    public void parseJsonLoadLand(String jsonstring){
        laenderListe = teamAct.getLaenderListe();
        JSONObject jsonObj=null;

        try {
            jsonObj = new JSONObject(jsonstring);


            JSONArray jArr = jsonObj.getJSONArray("teams");
            for(int i =0; i<jArr.length();i++){
                JSONObject subObj = jArr.getJSONObject(i);
                landId=subObj.getString("id");
                landName=subObj.getString("name");
                fifaCode=subObj.getString("fifaCode");
                iso2=subObj.getString("iso2");
                flag=subObj.getString("flag");
                emoji=subObj.getString("emoji");
                emojiString=subObj.getString("emojiString");
                land = new Land(landName,landId,fifaCode,flag,emoji,emojiString,iso2);
                laenderListe.add(land);
                alleLaender.add(land);
            }

        }
        catch (JSONException e){
            e.printStackTrace();
        }


    }
    //Liest alle Spiele aus dem JSON, speichert sie und zeit alle Spiele in der Statistik-Activity
    public void parseJsonSpiel(String jsonstring){
        spieleListe = teamAct.getSpieleListe();

        //Diese ArrayList braucht es, damit sie dem Spieladapter übergeben werden kann
        ArrayList<Spiele> spieleList = new ArrayList<>();
        JSONObject jsonObject=null;
        try{
            jsonObject = new JSONObject(jsonstring);
            JSONObject gr = jsonObject.getJSONObject("groups");
            //Auslesen der Gruppenspiele
            for(char i ='a'; i<='h';i++) {
                JSONObject subObj = gr.getJSONObject(i+"");
                JSONArray subjArr = subObj.getJSONArray("matches");
                for (int j = 0; j < subjArr.length(); j++) {
                    JSONObject subSubObj = subjArr.getJSONObject(j);
                    homeTeam = subSubObj.getString("home_team");
                    awayTeam = subSubObj.getString("away_team");
                    awayResult = subSubObj.getString("away_result");
                    homeResult = subSubObj.getString("home_result");
                    spielName = subSubObj.getString("name");
                    if(awayResult=="null"){
                        awayResult="-";
                        homeResult="-";
                    }
                    spiel = new Spiele(homeTeam, awayTeam, homeResult, awayResult, spielName,teamAct);
                    spieleListe.add(spiel); //Arrayadatper
                    spieleList.add(spiel);  //ArrayList
                }
            }

            //Auslesen der Knockoutspiele

            ArrayList<String> strKnockName = new ArrayList<String>();
            strKnockName.add("round_16");
            strKnockName.add("round_8");
            strKnockName.add("round_4");
            strKnockName.add("round_2_loser");
            strKnockName.add("round_2");


            JSONObject knockout = jsonObject.getJSONObject("knockout");

            for (String s : strKnockName) {
                JSONObject subObj = knockout.getJSONObject(s);
                JSONArray subjArr = subObj.getJSONArray("matches");
                //1/8-Final
                if(s=="round_16") {
                    for (int i = 0; i < subjArr.length(); i++) {
                        JSONObject subSubObj = subjArr.getJSONObject(i);
                        homeTeam = subSubObj.getString("home_team");
                        awayTeam = subSubObj.getString("away_team");
                        awayResult = subSubObj.getString("away_result");
                        homeResult = subSubObj.getString("home_result");
                        spielName = subSubObj.getString("name");
                        homePenalty = subSubObj.getString("home_penalty");
                        awayPenalty = subSubObj.getString("away_penalty");
                        winner = subSubObj.getString("winner");
                        spiel = new Spiele(homeTeam, awayTeam, homeResult, awayResult ,homePenalty, awayPenalty,spielName,winner,teamAct);
                        spieleListe.add(spiel); //ArrayAdapter
                        spieleList.add(spiel); //ArrayList
                    }
                }
                //Restliche Knockoutspiele
                else{
                    for (int i = 0; i < subjArr.length(); i++) {
                        JSONObject subSubObj = subjArr.getJSONObject(i);
                        homeTeam = subSubObj.getString("home_team");
                        awayTeam = subSubObj.getString("away_team");
                        awayResult = subSubObj.getString("away_result");
                        homeResult = subSubObj.getString("home_result");
                        spielName = subSubObj.getString("name");
                        homePenalty = subSubObj.getString("home_penalty");
                        awayPenalty = subSubObj.getString("away_penalty");
                        winner = subSubObj.getString("winner");
                        selectWinner(spieleList);
                        spiel = new Spiele(homeTeam, awayTeam, homeResult, awayResult ,homePenalty, awayPenalty,spielName,winner,teamAct);
                        spieleListe.add(spiel); //ArrayAdapter
                        spieleList.add(spiel); //ArrayList
                    }
                }
            }



            //Die Custom-View wird der Listview angehängt
            listView = (ListView) teamAct.findViewById(R.id.spielL);
            spieleAdapter = new SpieleAdapter(teamAct,spieleList);
            listView.setAdapter(spieleAdapter);
        }

        catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //Liest den Gewinner des vorherigen der 1/8,1/4,1/2-Finale
    private void selectWinner(ArrayList<Spiele> spieleList){
        switch (spielName){
            case "57":
                String winner1 = spieleList.get(48).winner;
                winnerIDtoName(winner1,48,spieleList,true);
                String winner2 = spieleList.get(49).winner;
                winnerIDtoName(winner2,49,spieleList,false);
                break;
            case "58":
                String winner3 = spieleList.get(52).winner;
                winnerIDtoName(winner3,52,spieleList,true);
                String winner4 = spieleList.get(53).winner;
                winnerIDtoName(winner4,53,spieleList,false);
                break;
            case "59":
                String winner6 = spieleList.get(50).winner;
                winnerIDtoName(winner6,50,spieleList,true);
                String winner7 = spieleList.get(51).winner;
                winnerIDtoName(winner7,51,spieleList,false);
                break;
            case "60":
                String winner8 = spieleList.get(54).winner;
                winnerIDtoName(winner8,54,spieleList,true);
                String winner9 = spieleList.get(55).winner;
                winnerIDtoName(winner9,55,spieleList,false);
                break;
            case "61":
                String winner10 = spieleList.get(56).winner;
                winnerIDtoName(winner10,56,spieleList,true);
                String winner11 = spieleList.get(57).winner;
                winnerIDtoName(winner11,57,spieleList,false);
                break;
            case "62":
                String winner12 = spieleList.get(58).winner;
                winnerIDtoName(winner12,58,spieleList,true);
                String winner13 = spieleList.get(59).winner;
                winnerIDtoName(winner13,59,spieleList,false);
                break;
            case "63":
                String looser1 = spieleList.get(60).winner;
                if(looser1=="home"){
                    this.homeTeam=spieleList.get(60).awayTeam;
                }
                else{
                    this.homeTeam=spieleList.get(60).homeTeam;
                }
                String looser2 = spieleList.get(61).winner;
                if(looser2=="home"){
                    this.awayTeam=spieleList.get(61).awayTeam;
                }
                else{
                    this.awayTeam=spieleList.get(61).homeTeam;
                }

                break;
            case  "64":
                String winner16 = spieleList.get(60).winner;
                winnerIDtoName(winner16,60,spieleList,true);
                String winner17 = spieleList.get(61).winner;
                winnerIDtoName(winner17,61,spieleList,false);
                break;
        }
    }
    //Setzt den Namen des Home und Away-Teams des Spieles.
    private void winnerIDtoName(String winnerN,int spielId,ArrayList<Spiele> spieleList,Boolean homeTeam){
        if(homeTeam){
            if(winnerN=="home"){
                this.homeTeam=spieleList.get(spielId).homeTeam;
            }
            else{
                this.homeTeam=spieleList.get(spielId).awayTeam;
            }
        }
        else {
            if(winnerN=="home"){
                this.awayTeam=spieleList.get(spielId).homeTeam;
            }
            else{
                this.awayTeam=spieleList.get(spielId).awayTeam;
            }
        }
    }}
