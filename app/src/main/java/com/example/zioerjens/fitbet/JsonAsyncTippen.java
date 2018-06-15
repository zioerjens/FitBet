package com.example.zioerjens.fitbet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    int counter;

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

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat cformat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = cformat.format(calendar.getTime());
        String currentTime;
        String[] currentDateTime=new String[2];
        SimpleDateFormat cformat2 = new SimpleDateFormat("HH:mm:ss");
        currentTime = cformat2.format(calendar.getTime());

        currentDateTime[0]=currentDate;
        currentDateTime[1]=currentTime;

        Log.w("Date Log",currentDate);

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
                    String[] spielDateArray = new String[2];
                    spielDateArray=dateSpliter(subSubObj);




                    if(tippOK(currentDateTime,spielDateArray)){
                        spiel = new Spiele(homeTeam, awayTeam, homeResult, awayResult, spielName,teamAct);
                        spieleListe.add(spiel); //Arrayadatper
                        spieleList.add(spiel);  //ArrayList

                    }
                    else {
                        counter++;

                    }
                }
            }

            //Auslesen der Knockoutspiele
            /*
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
            }*/



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
        int a = 57-counter;

        int s = Integer.parseInt(spielName);
        if(s==57-counter) {
            String winner1 = spieleList.get(48-counter).winner;
            winnerIDtoName(winner1, 48, spieleList, true);
            String winner2 = spieleList.get(49-counter).winner;
            winnerIDtoName(winner2, 49, spieleList, false);
        }
        else if(s==58-counter) {
            String winner3 = spieleList.get(52-counter).winner;
            winnerIDtoName(winner3, 52, spieleList, true);
            String winner4 = spieleList.get(53-counter).winner;
            winnerIDtoName(winner4, 53, spieleList, false);
        }

        else if(s==59-counter) {
            String winner6 = spieleList.get(50-counter).winner;
            winnerIDtoName(winner6, 50, spieleList, true);
            String winner7 = spieleList.get(51-counter).winner;
            winnerIDtoName(winner7, 51, spieleList, false);
        }
        else if(s==60-counter) {
            String winner8 = spieleList.get(54-counter).winner;
            winnerIDtoName(winner8, 54, spieleList, true);
            String winner9 = spieleList.get(55-counter).winner;
            winnerIDtoName(winner9, 55, spieleList, false);
        }
        else if(s==61-counter) {
            String winner10 = spieleList.get(56-counter).winner;
            winnerIDtoName(winner10, 56, spieleList, true);
            String winner11 = spieleList.get(57-counter).winner;
            winnerIDtoName(winner11, 57, spieleList, false);
        }
        else if(s==62-counter) {
            String winner12 = spieleList.get(58-counter).winner;
            winnerIDtoName(winner12, 58, spieleList, true);
            String winner13 = spieleList.get(59-counter).winner;
            winnerIDtoName(winner13, 59, spieleList, false);
        }
        else if(s==63-counter) {
            String looser1 = spieleList.get(60-counter).winner;
            if (looser1 == "home") {
                this.homeTeam = spieleList.get(60-counter).awayTeam;
            } else {
                this.homeTeam = spieleList.get(60-counter).homeTeam;
            }
            String looser2 = spieleList.get(61-counter).winner;
            if (looser2 == "home") {
                this.awayTeam = spieleList.get(61-counter).awayTeam;
            } else {
                this.awayTeam = spieleList.get(61-counter).homeTeam;
            }

        }
        else if(s==64-counter) {
            String winner16 = spieleList.get(60-counter).winner;
            winnerIDtoName(winner16, 60-counter, spieleList, true);
            String winner17 = spieleList.get(61-counter).winner;
            winnerIDtoName(winner17, 61-counter, spieleList, false);
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
    }

    public String[] dateSpliter(JSONObject jsonObject){
        String[] date = new String[2];
        try {

            Log.w("Date JSON",jsonObject.getString("date"));
            String datetime = jsonObject.getString("date");
            String time;
            String mez;
            date[0] = datetime.split("T")[0];
            time=datetime.split("T")[1];
            String sp= "\\+";

            mez = time.split(sp)[1];
            date[1] = time.split(sp)[0];
            date[1]=timeMez(date[1],mez);


            //Log.w("Datesolo mez:", date[1]);
            //Log.w("Datesolo date:",date[0]);
        }

        catch (JSONException e) {
            e.printStackTrace();
        }

        return date;
    }

    public String timeMez(String time, String difference){
        int hourint=Integer.parseInt(time.split(":")[0]);
        int difint=Integer.parseInt(difference.split(":")[0])-2;

        String hourStr;
        int newHour=hourint-difint;
        if(newHour<10){
            hourStr = "0"+newHour+":00:00";
        }
        else {
            hourStr = newHour+":00:00";
        }

        return hourStr;
    }

    public boolean tippOK(String[] currentTime,String[] matchTime){
        Boolean b = true;
        String[] Datum1 =currentTime[0].split("-");
        String[] Datum2 = matchTime[0].split("-");
        String[] Time1 = currentTime[1].split(":");
        String[] Time2 = matchTime[1].split(":");



        Date d1 = new Date(Integer.parseInt(Datum1[0]),Integer.parseInt(Datum1[1]),Integer.parseInt(Datum1[2]),Integer.parseInt(Time1[0]),Integer.parseInt(Time1[1]),Integer.parseInt(Time1[2]));
        Date d2 = new Date(Integer.parseInt(Datum2[0]),Integer.parseInt(Datum2[1]),Integer.parseInt(Datum2[2]),Integer.parseInt(Time2[0])-1,Integer.parseInt(Time2[1]),Integer.parseInt(Time2[2]));
;
        Log.w("d1",d1+"");
        Log.w("d2",d2+"");



        if(d1.after(d2)){
            Log.w("blabla","false");
            b=false;
        }

        return b;
    }
}
