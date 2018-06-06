package com.example.zioerjens.fitbet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
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

public class JsonAsynch extends AsyncTask<String,String,String> {

    private TestJsonParse teamAct;
    private String url2;
    private ProgressDialog mDialog;
    private ArrayAdapter laenderListe;
    private ArrayAdapter spieleListe;

    private String landName;
    private String landId;
    private String fifaCode;
    private String flag;
    private String emoji;
    private String emojiString;
    private String iso2;


    private String spielName;
    private  String spielType;
    private String homeTeam;
    private String awayTeam;
    private String homeResult;
    private String awayResult;
    private String spielDate;
    private String stadium;
    private String finished;
    private String matchday;

    private ListView listView;
    private SpieleAdapter spieleAdapter;


    private Spiele spiel;
    private Land land;






    public JsonAsynch(String url, TestJsonParse teamAct, ProgressDialog mDialog){
        this.url2 = url;
        this.teamAct = teamAct;
        this.mDialog = mDialog;


    }


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
    protected void onPostExecute(String result) {

        parseJsonLoadLand(result);
        parseJsonSpiel(result);

    }

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
            }

        }
        catch (JSONException e){
            e.printStackTrace();
        }


    }
    public void parseJsonSpiel(String jsonstring){
        spieleListe = teamAct.getSpieleListe();
        ArrayList<Spiele> spieleList = new ArrayList<>();
        JSONObject jsonObject=null;
        try{
            jsonObject = new JSONObject(jsonstring);


            JSONObject gr = jsonObject.getJSONObject("groups");

            for(char i ='a'; i<='h';i++) {
                JSONObject subObj = gr.getJSONObject(i+"");
                JSONArray subjArr = subObj.getJSONArray("matches");
                for (int j = 0; j < subjArr.length(); j++) {
                    JSONObject subSubObj = subjArr.getJSONObject(j);
                    homeTeam = subSubObj.getString("home_team");
                    awayTeam = subSubObj.getString("away_team");
                    awayResult = subSubObj.getString("away_result");
                    homeResult = subSubObj.getString("home_result");
                    if(awayResult=="null"){
                        awayResult="-";
                        homeResult="-";
                    }
                    spiel = new Spiele(homeTeam, awayTeam, homeResult, awayResult, teamAct);
                    spieleListe.add(spiel);
                    spieleList.add(spiel);
                }
            }

            listView = (ListView) teamAct.findViewById(R.id.spielL);
            spieleAdapter = new SpieleAdapter(teamAct,spieleList);
            listView.setAdapter(spieleAdapter);
        }

        catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
