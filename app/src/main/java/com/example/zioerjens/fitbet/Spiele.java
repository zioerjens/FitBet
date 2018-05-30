package com.example.zioerjens.fitbet;

import android.widget.ArrayAdapter;

public class Spiele {
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

    private TestJsonParse act;
    private ArrayAdapter laenderListe;
    private Land landHome;
    private Land landAway;

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

    public Spiele(String homeTeam, String awayTeam, TestJsonParse act){
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.act = act;

        laenderListe=act.getLaenderListe();
        landHome = (Land) laenderListe.getItem(Integer.parseInt(homeTeam)-1);
        landAway = (Land) laenderListe.getItem(Integer.parseInt(awayTeam)-1);

        homeTeamStr = landHome.getEmojiString()+" "+landHome.getFifaCode();
        awayTeamStr = landAway.getFifaCode()+" "+landAway.getEmojiString();
    }

    @Override
    public String toString() {
        return homeTeamStr+" - "+awayTeamStr;
    }
}
