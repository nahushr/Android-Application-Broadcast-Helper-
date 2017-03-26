package com.example.nahushraichura.broadcasthelper;


        import android.content.DialogInterface;
        import android.content.Intent;
        import android.location.LocationManager;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.provider.Settings;
        import android.support.v4.app.FragmentActivity;
        import android.os.Bundle;

        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.CameraPosition;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;

        import android.content.Context;
        import android.os.AsyncTask;
        import android.os.Handler;
        import android.support.v4.app.FragmentActivity;
        import android.os.Bundle;
        import android.support.v7.app.AlertDialog;
        import android.util.Log;
        import android.view.View;
        import android.widget.Toast;

        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.MarkerOptions;

        import org.apache.http.HttpResponse;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.impl.client.DefaultHttpClient;

        import java.io.BufferedReader;
        import java.io.InputStreamReader;
        import java.net.URI;
        import java.net.URL;
        import java.net.URLConnection;
        import java.net.URLEncoder;
        import java.util.Timer;
        import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private MarkerOptions markerOptions;
    private Marker m;
    private GoogleMap mMap;
    double latitude;
    double longitude;
    static int flag;
    LocationManager locationManager;
    boolean checkNetwork = false;

    boolean checkGPS = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        locationManager = (LocationManager) getApplicationContext()
                .getSystemService(LOCATION_SERVICE);
        // Add a marker in Sydney and move the camera
        callAsynchronousTask();
        /*LatLng user = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(user).title("current location"));
        CameraPosition cameraPosition= new CameraPosition.Builder().target(user).zoom(16).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(user));
    }
    public void onCenter(View view){

        CameraPosition cameraPosition= new CameraPosition.Builder().target(new LatLng(latitude,longitude)).zoom(10).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            LocationRetrieve locationRetrieve = new LocationRetrieve(getApplicationContext(),0);
                            // PerformBackgroundTask this class is the class that extends AsynchTask
                            locationRetrieve.execute();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 1000); //execute in every 50000 ms
    }
    class LocationRetrieve  extends AsyncTask<String, Void, String> {
        private Context context;
        private int byGetOrPost = 0;
        public String line="";


        //flag 0 means get and 1 means post.(By default it is get.)
        public LocationRetrieve(Context context,int flag) {
            this.context = context;
            byGetOrPost = flag;
        }

        protected void onPreExecute(){

            /*locationManager = (LocationManager) getApplicationContext()
                    .getSystemService(LOCATION_SERVICE);
            // getting GPS status
            checkGPS = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            checkNetwork = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!checkGPS || !checkNetwork) {*/
            if(!isNetworkAvailable())
                Toast.makeText(getApplicationContext(), "Cant connect to internet", Toast.LENGTH_SHORT).show();
            //showSettingsAlert();
        }

        private boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        /*public void showSettingsAlert() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());


            alertDialog.setTitle("GPS Not Enabled");

            alertDialog.setMessage("Do you wants to turn On GPS");


            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    getApplicationContext().startActivity(intent);
                }
            });


            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });


            alertDialog.show();
        }*/

        @Override
        protected String doInBackground(String... arg0) {
            if (!isNetworkAvailable())
                return "";
            if(byGetOrPost == 0){ //means by Get Method

                try{
                    /*String name = (String)arg0[0];
                    String email = (String)arg0[1];
                    String mobile = (String)arg0[2];
                    String dob = (String)arg0[3];
                    String age = (String)arg0[4];
                    Log.d("name", name);
                    Log.d("email",email);
                    Log.d("mobile",mobile);
                    Log.d("dob",dob);
                    Log.d("age",age);*/
                    //String link = "http://nahushrai.esy.es/dbit_insert.php?name="+name+"&email="+email+"&mobile="+mobile+"&dob="+dob+"&age="+age;
                    String email=getIntent().getStringExtra("email");
                   // email="nahushrai@gmail.com";
                    String link="http://nahushrai.esy.es/sendmap.php?email="+email ;
                    URL url = new URL(link);
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    request.setURI(new URI(link));
                    HttpResponse response = client.execute(request);
                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(response.getEntity().getContent()));




                    while ((line = in.readLine()) != null) {
                        // set latitude and longitude

                        break;
                    }

                    in.close();
                    return line;
                } catch(Exception e){
                    Toast.makeText(getApplicationContext(),"check GPS and Network",Toast.LENGTH_SHORT).show();
                    return "";
                }
            } else{
                try{
                    String email = (String)arg0[0];
                    String password = (String)arg0[1];

                    String link="http://nahushrai.esy.es/login.php";
                    String data  = URLEncoder.encode("email", "UTF-8") + "=" +
                            URLEncoder.encode(email, "UTF-8");
                    data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                            URLEncoder.encode(password, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    //conn.setDoOutput(true);
                    conn.setDoInput(true);
                    //OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    //wr.write( data );
                    //wr.flush();


                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }

                    return sb.toString();
                } catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }
            }
        }

        @Override
        protected void onPostExecute(String result){
            try {
                if (result == "")
                    return;
                String a[] = result.split(",");
                Log.e("latlong", a[0] + " " + a[1]);
                latitude = Double.parseDouble(a[0]);
                longitude = Double.parseDouble(a[1]);
                //mMap.clear();
                if (flag == 0) {
                    LatLng user = new LatLng(latitude, longitude);
                    markerOptions = new MarkerOptions().position(user);
                    m = mMap.addMarker(markerOptions);
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(user).zoom(16).build();
                    mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    flag = 1;
                } else {
                    m.setPosition(new LatLng(latitude, longitude));
                }
            /*LatLng user = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(user).title("current location"));
            CameraPosition cameraPosition= new CameraPosition.Builder().target(user).zoom(26).build();
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            */
                Toast.makeText(getApplicationContext(), latitude + "  " + longitude, Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                Log.e("post",e.getMessage());
            }
        }
    }
}
