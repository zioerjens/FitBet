package com.example.zioerjens.fitbet;

public class SportData {

    double distance;
    double multiplier;
    int counter;
    String uid;
    double altitude;

    public SportData(){

    }

    public SportData(String u,double d, double m, int c, double a){
        this.distance = d;
        this.multiplier = m;
        this.counter = c;
        this.uid = u;
        this.altitude = a;
    }

    public SportData(double d, double m, int c, double a){
        this.distance = d;
        this.multiplier = m;
        this.counter = c;
        this.altitude = a;
    }
}
