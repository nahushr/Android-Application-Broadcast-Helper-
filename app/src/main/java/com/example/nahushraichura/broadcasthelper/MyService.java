package com.example.nahushraichura.broadcasthelper;



import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;



public class MyService extends Service {

    private LocationListener listener;
    private LocationManager locationManager;
    public static String email;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(),"service started",Toast.LENGTH_LONG).show();
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        email=pref.getString("Emailkey","");
        Log.d("service_string",email);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                try {
                    String line = new Updating(getApplicationContext(), 0).execute(Double.toString(location.getLatitude()), Double.toString(location.getLongitude()), email).get();

                    Log.d("service_line_string ", line);
                    line = line.trim();
                    if (line.equalsIgnoreCase("UpdatedUpdated")) {
                        Toast.makeText(getApplicationContext(),Double.toString(location.getLatitude())+"+"+Double.toString(location.getLongitude()) , Toast.LENGTH_SHORT).show();
                        Intent i = new Intent("location_update");
                        i.putExtra("coordinates", location.getLongitude() + " " + location.getLatitude());
                        sendBroadcast(i);
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        //noinspection MissingPermission
        locationManager.requestLocationUpdates("gps",3000,0,listener);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager != null){
            //noinspection MissingPermission
            locationManager.removeUpdates(listener);
        }
    }

}
