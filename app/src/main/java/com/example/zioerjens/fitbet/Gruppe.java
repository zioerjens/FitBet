package com.example.zioerjens.fitbet;

//Dient dem Einspeichern in die Datenbank
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
