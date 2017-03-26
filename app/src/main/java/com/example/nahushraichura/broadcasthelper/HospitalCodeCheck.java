package com.example.nahushraichura.broadcasthelper;



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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

class HospitalCodeCheck  extends AsyncTask<String, Void, String>{
    private Context context;
    private int byGetOrPost = 0;
    public String line="";
    ProgressDialog progressDialog;

    //flag 0 means get and 1 means post.(By default it is get.)
    public HospitalCodeCheck(Context context,int flag) {
        this.context = context;
        byGetOrPost = flag;
    }

    protected void onPreExecute(){
       // progressDialog = ProgressDialog.show(context, "", "msg", true);
    }

    @Override
    protected String doInBackground(String... arg0) {
        if(byGetOrPost == 0){ //means by Get Method

            try{
                String hospitalcode = (String)arg0[0];

                Log.d("push_message",hospitalcode);
                hospitalcode=hospitalcode.trim();


                String link = "http://nahushrai.esy.es/dbit_get_hosp.php?code="+hospitalcode;

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
        //progressDialog.dismiss();
    }
}