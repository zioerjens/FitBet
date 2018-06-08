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

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Sport extends AppCompatActivity {

    public LocationManager locMan;
    private LocationListener locList;
    public Location tempLoc = null;
    private double distance;
    private double alti;
    private double multiplier;
    private int actualProgress;

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
    protected void onResume(){
        super.onResume();
        checkLocPermission();
        registerGPS();
        //ToDo loadMultiplier() + loadActualProgress

        //ToDo remove
        fillMultiplier();
    }

    @Override
    protected void onPause(){
        super.onPause();
        //ToDO insertMultiplier() + insertActualProgress
    }

    private void checkLocPermission(){
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  }, 1);
        }
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  }, 2);
        }
    }

    private void registerGPS(){
        locMan = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locList = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(tempLoc.getLatitude() != 0.0 && tempLoc.getLongitude() != 0.0 && tempLoc != null ){
                    distance += location.distanceTo(tempLoc);
                    if(location.getAltitude()>tempLoc.getAltitude()){
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

    private void calcMultiplier(){
        int requirement = 10;
        double progress;
        if(distance>requirement){

            int counter = (int) (distance / requirement );

            for(int i = 0; i<counter;i++){
                multiplier += 0.1;
                distance -= requirement;
            }
        }
        setProgress(distance / requirement);

    }

    private void setProgress(double progress){
        ProgressBar p = findViewById(R.id.distanceProgressBar);
        actualProgress = (int)(100*progress);
        p.setProgress(actualProgress);
    }

    private void fillTextviews(){
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);

        final TextView tvDistance = findViewById(R.id.distanceAmmount);
        tvDistance.setText(df.format(distance));

        final TextView tvUpA = findViewById(R.id.upwardSlopeAmmount);
        tvUpA.setText(df.format(alti));

        fillMultiplier();

        //debugging/Testing
        final TextView tvUp = findViewById(R.id.upwardSlope);
        tvUp.setText(df.format(tempLoc.getLatitude()));
    }

    private void fillMultiplier(){
        if(multiplier == 0){
            this.multiplier = 1.1;
            this.actualProgress = 0;
        }
        DecimalFormat mdf = new DecimalFormat("#.#");
        mdf.setRoundingMode(RoundingMode.CEILING);

        final TextView tvMultiplier = findViewById(R.id.multiplicatorScore);
        tvMultiplier.setText(mdf.format(multiplier));

        setProgress(0);
    }

    public void onStartClicked() {
        try {
            locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0.1f, locList);
        }
        catch(SecurityException e){
            Log.v("Location Permission err",e.getMessage());
        }
        if(tempLoc == null){
            tempLoc = new Location(LocationManager.GPS_PROVIDER);
        }
        Toast t =Toast.makeText(getApplicationContext(),"started",Toast.LENGTH_LONG);

        t.show();
    }

    public void onStopClicked(){
        locMan.removeUpdates(locList);
        Toast t =Toast.makeText(getApplicationContext(),"stoped",Toast.LENGTH_LONG);
        final TextView tvUp = findViewById(R.id.upwardSlope);
        tvUp.setText("Upward slope");
        t.show();
    }



}
