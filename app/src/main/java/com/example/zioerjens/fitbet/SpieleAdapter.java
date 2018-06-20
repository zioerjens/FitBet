package com.example.zioerjens.fitbet;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SpieleAdapter extends ArrayAdapter<Spiele> {

    private Context mContext;
    private List<Spiele> spieleList = new ArrayList<>();
    private TextView homeTipp;
    private TextView awayTipp;


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
        //Spielobjekt an der betreffenden Stelle
        Spiele currentSpiel = spieleList.get(position);
        //Die Knockout-Spiele haben eine andere Hintergrundfarbe
        if(position>=48){
            LinearLayout ll = (LinearLayout) listItem.findViewById(R.id.llback);
            ll.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        }
        if(position<48){
            LinearLayout ll = (LinearLayout) listItem.findViewById(R.id.llback);
            ll.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        }
        //Elemente der Custom-View
        TextView team = (TextView) listItem.findViewById(R.id.stat_land_home_name);
        TextView team2 = (TextView) listItem.findViewById(R.id.stat_land_away_name);
        TextView res1 = (TextView) listItem.findViewById(R.id.stat_land_home_result);
        TextView res2 = (TextView) listItem.findViewById(R.id.stat_land_away_result);
        homeTipp = (TextView) listItem.findViewById(R.id.homeTipp);
        awayTipp = (TextView) listItem.findViewById(R.id.awayTipp);
        Log.e("DAVOR", currentSpiel.spielName);

        fillTipps(currentSpiel.spielName, homeTipp, awayTipp);

        //Inhalt der Spiel Klasse wird gesetzt
        team.setText(currentSpiel.homeTeam);
        team2.setText(currentSpiel.awayTeam);
        res1.setText(currentSpiel.homeResult);
        res2.setText(currentSpiel.awayResult);

        return listItem;
    }

    private boolean fillTipps(final String spielName2, final TextView homeTipp, final TextView awayTipp){
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("tipp_user");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot grSnapshot : dataSnapshot.getChildren()) {
                    // Get Post object and use the values to update the UI
                    TippUser post = grSnapshot.getValue(TippUser.class);

                    Log.e("TIPP", post.tipp.spielName);
                    Log.e("SPIELGELADEN", spielName2);

                    if (post.user.userID.equals(uid) && post.tipp.spielName.equals(spielName2)){
                        homeTipp.setText(post.tipp.tippHome);
                        awayTipp.setText(post.tipp.tippAway);
                        Log.e("INSERTING", "INSERTINGNOW");
                    } else {
                        homeTipp.setText("");
                        awayTipp.setText("");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        return true;
    }
}
