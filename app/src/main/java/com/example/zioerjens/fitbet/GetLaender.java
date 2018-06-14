package com.example.zioerjens.fitbet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
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

public class GetLaender extends AsyncTask<String, String, String> {

    private Tippen_spielauswahlCountry activity;
    private View view;
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

    private Land land;

    public GetLaender(String url, Tippen_spielauswahlCountry activity, View view, ProgressDialog mDialog) {
        this.url2 = url;
        this.activity = activity;
        this.view = view;
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
    /*
    protected void onPostExecute(String result) {

        parseJsonLoadLand(result);
        parseJsonSpiel(result);

    }
    //Liest die Länder aus dem JSON und speichert diese als Land-Objekt Arryadapter
    public void parseJsonLoadLand(String jsonstring) {
        laenderListe = teamAct.getLaenderListe();
        JSONObject jsonObj = null;

        try {
            jsonObj = new JSONObject(jsonstring);


            JSONArray jArr = jsonObj.getJSONArray("teams");
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject subObj = jArr.getJSONObject(i);
                landId = subObj.getString("id");
                landName = subObj.getString("name");
                fifaCode = subObj.getString("fifaCode");
                iso2 = subObj.getString("iso2");
                flag = subObj.getString("flag");
                emoji = subObj.getString("emoji");
                emojiString = subObj.getString("emojiString");
                land = new Land(landName, landId, fifaCode, flag, emoji, emojiString, iso2);
                laenderListe.add(land);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
}