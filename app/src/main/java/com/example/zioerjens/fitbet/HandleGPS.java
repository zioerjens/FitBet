package com.example.zioerjens.fitbet;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

public class HandleGPS {

    public class gpsActivity extends AppCompatActivity {

        public LocationManager locMan;
        private LocationListener locList;
        private Location loc;
        double lat_x;
        double long_y;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        protected void onResume(){
            super.onResume();
            if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

                ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  }, 1);
            }
            if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

                ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  }, 2);
            }
            locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, locList);
            loc = new Location(locMan.GPS_PROVIDER);
        }

        @Override
        protected void onPause(){
            super.onPause();
            locMan.removeUpdates(locList);
        }

        private void initLocationServices() {
            locMan = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);



            locList = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    loc = location;
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

    }

}
