package com.example.nahushraichura.broadcasthelper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

class FinalRegistration  extends AsyncTask<String, Void, String> {
    private Context context;
    private int byGetOrPost = 0;
    public String line="";

    //flag 0 means get and 1 means post.(By default it is get.)
    public FinalRegistration(Context context,int flag) {
        this.context = context;
        byGetOrPost = flag;
    }

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
        if(byGetOrPost == 0){ //means by Get Method

            try{
                //name,email,mobile,dob,age,qr
                String name = (String)arg0[0];
                String email = (String)arg0[1];
                String mobile = (String)arg0[2];
                String dob = (String)arg0[3];
                String age = (String)arg0[4];
                String qr = (String)arg0[5];

                //Log.d("name",token);

                String link = "http://nahushrai.esy.es/dbit_insert_final.php?email="+email+"&name="+name+"&age="+age+"&mobile="+mobile+"&dob="+dob+"&qr="+qr;

                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");


                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
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

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

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
        Log.d("linestring",line);
        if(line.equalsIgnoreCase("0 results")==true)
        {
            Log.d("login_successful","false");
        }
        else {
            Log.d("String result", result);
            Log.d("login_successful", "true");


        }
    }
}