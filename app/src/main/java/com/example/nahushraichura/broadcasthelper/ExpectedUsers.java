package com.example.nahushraichura.broadcasthelper;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class ExpectedUsers extends Fragment {

    RecyclerView recyclerView;
    DataAdapter2 adapter2;
    String userdata[]=new String[6];
//name,email,mobile,dob,age,qr

    String myJSON;
    public String name,email,mobile,dob,age,qr;
    public boolean sponsorsflag = false;
    private static final String TAG_RESULTS_SPONSOR = "result";
    private static final String TAG_SPONSOR_NAME = "name";
    private static final String TAG_SPONSOR_EMAIL = "email";
    private static final String TAG_SPONSOR_MOBILE = "mobile";
    private static final String TAG_SPONSOR_DOB = "dob";
    private static final String TAG_SPONSOR_AGE = "age";
    private static final String TAG_SPONSOR_QR = "qr";
    JSONArray sponsorjson = null;
    ArrayList<HashMap<String, String>> sponsorlists;
    public  LinkedList llsponsorname = new LinkedList();
    public  LinkedList llsponsorposter = new LinkedList();
    public  LinkedList llsponsorurl = new LinkedList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expectedusers, container, false);


        recyclerView= (RecyclerView)view.findViewById(R.id.card_recycler_view);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View viewx, int position) {
                TextView useremail=(TextView)viewx.findViewById(R.id.email);
                Toast.makeText(getActivity(),useremail.getText().toString(),Toast.LENGTH_LONG).show();
                // Intent in=Intent(this,MapsActivity.class);
                //in.putExtra("email",useremail.getText().toString().trim());
                //startActivity(in);



            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        sponsorlists = new ArrayList<HashMap<String, String>>();
        getSponsorDatasql();


        return view;

    }
    private void initViews(){

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList androidVersions2 = prepareData();
        adapter2 = new DataAdapter2(getActivity(),androidVersions2);
        recyclerView.setAdapter(adapter2);

    }
    private ArrayList prepareData(){

        String eventday="",eventmonth="";

        Log.d("prepareflag","true");
        ArrayList android_version2 = new ArrayList<>();
        for(int i=0;i<llsponsorname.size();i++){
            AndroidVersions2 androidVersion2 = new AndroidVersions2();
            Log.d("exusername",llsponsorname.get(i).toString());
            androidVersion2.setusername(llsponsorname.get(i).toString());

            Log.d("exuseremail",llsponsorposter.get(i).toString());
            androidVersion2.setuseremail(llsponsorposter.get(i).toString());

            Log.d("exusermobile",llsponsorurl.get(i).toString());
            androidVersion2.setmobile(llsponsorurl.get(i).toString());


            android_version2.add(androidVersion2);
        }
        return android_version2;
    }
    public void getSponsorDatasql(){
        class GetsponsorDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://nahushrai.esy.es/dbit_get_exp.php");
                // Log.d("in link class","true");

                // Depends on your web service
                httppost.setHeader("Content-type", "application/json");

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                showsponsorlist();
                // Intent intent = new Intent(SetupActivity.this, WelcomeActivity.class);
                //startActivity(intent);
                sponsorsflag=true;
                initViews();

            }
        }
        GetsponsorDataJSON g = new GetsponsorDataJSON();
        try {
            String result= g.execute().get();
            Log.d("result string ",result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    protected void showsponsorlist(){
        //Log.d("in method","true");
        try {
            JSONObject jsonObj = new JSONObject(myJSON);

            sponsorjson = jsonObj.getJSONArray(TAG_RESULTS_SPONSOR);

            for(int i=0;i<sponsorjson.length();i++){
                JSONObject c = sponsorjson.getJSONObject(i);

                name = c.optString(TAG_SPONSOR_NAME);
                email=c.optString(TAG_SPONSOR_EMAIL);
                mobile=c.optString(TAG_SPONSOR_MOBILE);
                dob = c.optString(TAG_SPONSOR_DOB);
                age=c.optString(TAG_SPONSOR_AGE);
                qr=c.optString(TAG_SPONSOR_QR);


                Log.d("regname",name);
                Log.d("regemail",email);
                Log.d("regmobile",mobile);
                Log.d("regdob",dob);
                Log.d("regage",age);
                Log.d("regqr",qr);

                llsponsorname.add(name);
                llsponsorposter.add(email);
                llsponsorurl.add(mobile);




                HashMap<String,String> persons = new HashMap<String,String>();

                // persons.put(TAG_EVENT_GALLERY,urlstr);


                sponsorlists.add(persons);
                //sponsorsflag=true;
            }
            Log.d("sponsors flag","true");



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }



        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
