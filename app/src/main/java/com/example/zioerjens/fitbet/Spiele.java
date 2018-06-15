package com.example.zioerjens.fitbet;

import android.widget.ArrayAdapter;

import java.io.Serializable;

//Objekt mit Instanzvariabeln und vielen verschiedenen Konstruktoren, für alle verschiedenen Anwendungsfälle
public class Spiele implements Serializable {
    String spielName;
    String spielType;
    String homeTeam;
    String awayTeam;
    String homeResult;
    String awayResult;
    String spielDate;
    String stadium;
    String finished;
    String matchday;
    String winner;
    String homePenalty;
    String awayPenalty;

    private TestJsonParse act;
    private TippenAllGames act2;
    private ArrayAdapter laenderListe;
    Land landHome;
    Land landAway;

    private String homeTeamStr;
    private String awayTeamStr;



    public Spiele(String spielName, String spielType, String homeTeam, String awayTeam, String homeResult, String awayResult, String spielDate, String stadium, String finished, String matchday) {
        this.spielName = spielName;
        this.spielType = spielType;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeResult = homeResult;
        this.awayResult = awayResult;
        this.spielDate = spielDate;
        this.stadium = stadium;
        this.finished = finished;
        this.matchday = matchday;
    }

    public Spiele(String spielName, String spielType, String homeTeam, String awayTeam, String homeResult, String awayResult, String homePenalty, String awayPenalty, String spielDate, String stadium, String finished, String matchday) {
        this.spielName = spielName;
        this.spielType = spielType;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeResult = homeResult;
        this.awayResult = awayResult;
        this.spielDate = spielDate;
        this.stadium = stadium;
        this.finished = finished;
        this.matchday = matchday;
        this.homePenalty = homePenalty;
        this.awayPenalty = awayPenalty;
    }

    public Spiele(String homeTeam, String awayTeam, String homeResult, String awayResult, String spielName, TestJsonParse act){
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeResult =homeResult;
        this.awayResult = awayResult;
        this.act = act;
        this.spielName = spielName;

        laenderListe=act.getLaenderListe();
        try{
            landHome = (Land) laenderListe.getItem(Integer.parseInt(homeTeam)-1);
            landAway = (Land) laenderListe.getItem(Integer.parseInt(awayTeam)-1);
            this.homeTeam = landHome.getEmojiString()+" "+landHome.getFifaCode();
            this.awayTeam = landAway.getFifaCode()+" "+landAway.getEmojiString();
        }
        catch (Exception e){

            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
        }
    }

    //Der obere Konstruktor wurde wiederholt, jedoch mit einer anderen Activity, die mitgegeben wird.

    public Spiele(String homeTeam, String awayTeam, String homeResult, String awayResult, String spielName, TippenAllGames act){
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeResult =homeResult;
        this.awayResult = awayResult;
        this.act2 = act;
        this.spielName = spielName;

        laenderListe=act2.getLaenderListe();
        try{
            landHome = (Land) laenderListe.getItem(Integer.parseInt(homeTeam)-1);
            landAway = (Land) laenderListe.getItem(Integer.parseInt(awayTeam)-1);
            this.homeTeam = landHome.getEmojiString()+" "+landHome.getFifaCode();
            this.awayTeam = landAway.getFifaCode()+" "+landAway.getEmojiString();
        }
        catch (Exception e){

            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
        }
    }

    public Spiele(String homeTeam, String awayTeam, String homeResult, String awayResult, String homePenalty, String awayPenalty, String spielName, String winner, TestJsonParse act){
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeResult =homeResult;
        this.awayResult = awayResult;
        this.act = act;
        this.spielName = spielName;
        this.homePenalty = homePenalty;
        this.awayPenalty = awayPenalty;
        this.winner = winner;


        laenderListe=act.getLaenderListe();
        try{
            landHome = (Land) laenderListe.getItem(Integer.parseInt(homeTeam)-1);
            landAway = (Land) laenderListe.getItem(Integer.parseInt(awayTeam)-1);
            this.homeTeam = landHome.getEmojiString()+" "+landHome.getFifaCode();
            this.awayTeam = landAway.getFifaCode()+" "+landAway.getEmojiString();
        }
        catch (Exception e){

            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
        }

    }

    //Der obere Konstruktor wurde wiederholt, jedoch mit einer anderen Activity, die mitgegeben wird.

    public Spiele(String homeTeam, String awayTeam, String homeResult, String awayResult, String homePenalty, String awayPenalty, String spielName, String winner, TippenAllGames act){
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeResult =homeResult;
        this.awayResult = awayResult;
        this.act2 = act;
        this.spielName = spielName;
        this.homePenalty = homePenalty;
        this.awayPenalty = awayPenalty;
        this.winner = winner;


        laenderListe=act2.getLaenderListe();
        try{
            landHome = (Land) laenderListe.getItem(Integer.parseInt(homeTeam)-1);
            landAway = (Land) laenderListe.getItem(Integer.parseInt(awayTeam)-1);
            this.homeTeam = landHome.getEmojiString()+" "+landHome.getFifaCode();
            this.awayTeam = landAway.getFifaCode()+" "+landAway.getEmojiString();
        }
        catch (Exception e){

            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
        }

    }

    @Override
    public String toString() {
        return homeTeam+" - "+awayTeam;
    }
}
