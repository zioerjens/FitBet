package com.example.zioerjens.fitbet;

public class SportData {

    public double distance;
    public double multiplier;
    public int counter;
    public String uid;
    public double altitude;

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
