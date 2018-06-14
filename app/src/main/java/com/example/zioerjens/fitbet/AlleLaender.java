package com.example.zioerjens.fitbet;

import java.io.Serializable;
import java.util.ArrayList;

public class AlleLaender implements Serializable {

    private ArrayList<Land> alleLaender;

    public AlleLaender(ArrayList<Land> alleLaender){
        this.alleLaender = alleLaender;
    }
}
