package com.example.nahushraichura.broadcasthelper;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.Map;
import java.io.*;
import java.net.*;

public class UserQr extends AppCompatActivity {

    private ImageView userqr;
    static public int MARGIN_AUTOMATIC = -1;
    static public int MARGIN_NONE = 0;
    public static final String MyPREFERENCES = "MyPrefs";
    final int colorQR = Color.BLACK;
    final int colorBackQR = Color.WHITE;
    final int width = 400;
    final int height = 400;
    public static String mEncodeString;
    Bitmap bitmapQR;
    String URL = "http://www.androidbegin.com/wp-content/uploads/2013/07/HD-Logo.gif";
    ImageView image;
    Button button,bt_stop;
    ProgressDialog mProgressDialog;
    public InputStream input;
    SharedPreferences sharedpreferences;

    public String qremail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userqr);
        image = (ImageView) findViewById(R.id.userqr);
        button= (Button) findViewById(R.id.button2);
        bt_stop= (Button) findViewById(R.id.bt_stop);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),MyService.class);
                startService(i);
            }
        });

        bt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),MyService.class);
                stopService(i);
            }
        });

        //sharedpreferences= getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if(getIntent().hasExtra("email")) {

            qremail = getIntent().getStringExtra("email");
            // sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("Emailkey",qremail);
            editor.commit();

            String xyz=sharedpreferences.getString("Emailkey","");
            Log.d("qremail check",xyz);
        }
        else
        {
            qremail = sharedpreferences.getString("Emailkey","Not found");
        }

        try {
            String tp=new Init_qr(this,0).execute("tp").get();
            Log.d("qremail",qremail);
            qremail=qremail.trim();
            String line = new QR_retrieve(this, 0).execute(qremail).get();
            line = line.trim();
            Log.d("user qr",line);
            if (line.equalsIgnoreCase("error")) {
                Toast.makeText(this, "error retrieving qr", Toast.LENGTH_LONG).show();
            }

            else
            {
                Picasso.with(this)
                        .load(line)
                        .into(image);
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}