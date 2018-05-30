package com.example.zioerjens.fitbet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
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

    private Activity teamAct;
    private String url;


    public JsonAsynch(String url, Activity teamAct){
        this.url = url;
        this.teamAct = teamAct;
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


        } catch (Exception e) {
            //Log.v(BadiDetailsActivity.TAG, e.toString());
        }
        return msg.toString();
    }
    protected void onPostExecute(String result) {



    }

    public void parseJson(String jsonstring){
        ArrayAdapter ArrTeam = new ArrayAdapter<String>(teamAct, android.R.layout.simple_list_item_1);
        JSONObject jsonObj=null;

        try {
            jsonObj = new JSONObject(jsonstring);

            JSONObject teams = jsonObj.getJSONObject("teams");
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }


}
