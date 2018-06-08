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

public class Sport extends AppCompatActivity {

    public LocationManager locMan;
    private LocationListener locList;
    public Location tempLoc = null;
    private double distance;

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
                if(tempLoc != null){
                    distance += location.distanceTo(tempLoc);
                }
                tempLoc = location;
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

    public void onStartClicked() {
        try {
            locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locList);
        }
        catch(SecurityException e){
            Log.v("Location Permission err",e.getMessage());
        }
    }

    public void onStopClicked(){
        locMan.removeUpdates(locList);
    }



}
