package com.example.zioerjens.fitbet;

import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

public class Tippen_spielauswahlGroup extends Fragment {

    private Spinner spinner;
    private View activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tippen__spielauswahl_group,container,false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        activity = this.getView();
        spinner = (Spinner) activity.findViewById(R.id.groupSpinner);
        addGroupsToSpinner();
    }

    public void addGroupsToSpinner(){
        //TODO gruppen zu dropdown hinzufügen
    }
}
