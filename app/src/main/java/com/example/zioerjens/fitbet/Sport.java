package com.example.zioerjens.fitbet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Sport extends AppCompatActivity {

    private LocationManager locMan;
    private LocationListener locList;
    private Location tempLoc = null;
    private double distance;
    private double alti;
    private double multiplier;
    private int actualProgress;
    private int counter;
    private String uid = "c";

    private DatabaseReference sportRef;
    private SportData sd;

    private static volatile double multiplikator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        final Button buttonStart = findViewById(R.id.btnGo);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onStartClicked();
            }
        });


        final Button buttonStop = findViewById(R.id.btnStop);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onStopClicked();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLocPermission();
        registerGPS();
        getAcc();
        sportRef = FirebaseDatabase.getInstance().getReference("sportler");

        loadFromFirebase();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy(){
        insertIntoFirebase();
        super.onDestroy();
    }

    private void checkLocPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        }
    }

    private void registerGPS() {
        locMan = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locList = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (tempLoc.getLatitude() != 0.0 && tempLoc.getLongitude() != 0.0 && tempLoc != null) {
                    distance += location.distanceTo(tempLoc);
                    if (location.getAltitude() > tempLoc.getAltitude()) {
                        alti += location.getAltitude() - tempLoc.getAltitude();
                    }
                }
                tempLoc = location;
                calcMultiplier();
                fillTextviews();
            }


            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };
    }

    private void calcMultiplier() {
        int requirement = 50;
        double delta = distance - (counter * requirement);
        if (delta > requirement) {

            int mutliplierSteps = (int) (delta / requirement);

            for (int i = 0; i < mutliplierSteps; i++) {
                multiplier += 0.1;
                counter++;
            }
        }
        setProgress(delta / requirement);

    }

    private void setProgress(double progress) {
        ProgressBar p = findViewById(R.id.distanceProgressBar);
        actualProgress = (int) (100 * progress);
        p.setProgress(actualProgress);
    }

    private void fillTextviews() {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);

        final TextView tvDistance = findViewById(R.id.distanceAmmount);
        tvDistance.setText(df.format(distance));

        final TextView tvUpA = findViewById(R.id.upwardSlopeAmmount);
        tvUpA.setText(df.format(alti));

        DecimalFormat mdf = new DecimalFormat("#.#");
        mdf.setRoundingMode(RoundingMode.CEILING);

        final TextView tvMultiplier = findViewById(R.id.multiplicatorScore);
        tvMultiplier.setText(mdf.format(multiplier));

        calcMultiplier();
    }

    private void fillData() {
        if (sd != null) {
            this.multiplier = sd.multiplier;
            this.counter = sd.counter;
            this.alti = sd.altitude;
            this.distance = sd.distance;
            this.alti = sd.altitude;
        } else if (multiplier == 0) {
            this.multiplier = 1;
            this.counter = 0;
            this.actualProgress = 0;
            this.distance = 0;
            this.alti = 0;
        }
    }

    public void onStartClicked() {
        try {
            locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, locList);
        } catch (SecurityException e) {
            Log.v("Location Permission err", e.getMessage());
        }
        if (tempLoc == null) {
            tempLoc = new Location(LocationManager.GPS_PROVIDER);
        }
        Toast t = Toast.makeText(getApplicationContext(), "started", Toast.LENGTH_LONG);

        t.show();
        fillTextviews();
    }

    public void onStopClicked() {
        locMan.removeUpdates(locList);
        Toast t = Toast.makeText(getApplicationContext(), "stoped", Toast.LENGTH_LONG);
        t.show();
        insertIntoFirebase();
    }

    public void insertIntoFirebase() {
        sportRef.child(uid).setValue(new SportData(distance, multiplier, counter, alti));
    }

    public void loadFromFirebase() {
        DatabaseReference actualData = sportRef.child(uid);
        actualData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sd = dataSnapshot.getValue(SportData.class);
                fillData();
                fillTextviews();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getAcc() {
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Deprecated
    public static synchronized double getMultiplier(String userId){
        DatabaseReference actualData = FirebaseDatabase.getInstance().getReference("sportler").child(userId);
        actualData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    SportData sdTemp = dataSnapshot.getValue(SportData.class);
                    multiplikator = sdTemp.multiplier;
                }
                catch(NullPointerException e){
                    multiplikator = 1.0;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return multiplikator;
    }
}
