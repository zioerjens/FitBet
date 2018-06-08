package com.example.zioerjens.fitbet;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SpieleAdapter extends ArrayAdapter<Spiele> {

    private Context mContext;
    private List<Spiele> spieleList = new ArrayList<>();


    public SpieleAdapter(@NonNull Context context, @LayoutRes ArrayList<Spiele> list) {
        super(context, 0,list);
        this.mContext = context;
        this.spieleList = list;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem==null){
            listItem= LayoutInflater.from(mContext).inflate(R.layout.customlayout_stat,parent,false);
        }

        Spiele currentSpiel = spieleList.get(position);
        if(position>=48){
            LinearLayout ll = (LinearLayout) listItem.findViewById(R.id.llback);
            ll.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        }
        if(position<48){
            LinearLayout ll = (LinearLayout) listItem.findViewById(R.id.llback);
            ll.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        }

        TextView team = (TextView) listItem.findViewById(R.id.stat_land_home_name);
        TextView team2 = (TextView) listItem.findViewById(R.id.stat_land_away_name);
        TextView res1 = (TextView) listItem.findViewById(R.id.stat_land_home_result);
        TextView res2 = (TextView) listItem.findViewById(R.id.stat_land_away_result);
        team.setText(currentSpiel.homeTeam);
        team2.setText(currentSpiel.awayTeam);
        res1.setText(currentSpiel.homeResult);
        res2.setText(currentSpiel.awayResult);

        return listItem;
    }
}
