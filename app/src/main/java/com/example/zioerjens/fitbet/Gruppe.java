package com.example.zioerjens.fitbet;

public class Gruppe {

    public String name;
    public String passwort;

    public Gruppe(String name, String passwort){
        this.name = name;
        this.passwort = passwort;
    }
    public Gruppe(){}

    @Override
    public String toString() {
        return name;
    }
}
