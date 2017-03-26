package com.example.nahushraichura.broadcasthelper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class Loginactivity extends AppCompatActivity {
    private Button user,hospital;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);



        user=(Button)findViewById(R.id.user);
        hospital=(Button)findViewById(R.id.hospital);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start user activity
                SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
                if (pref.getBoolean("activity_executed", false)) {
                    Intent in=new Intent(Loginactivity.this,UserQr.class);
                    startActivity(in);
                    finish();
                } else {
                    Intent in=new Intent(Loginactivity.this,SignUpActivity.class);
                    startActivity(in);
                    finish();
                }

            }
        });
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start hospital activity
                SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
                if (pref.getBoolean("activity_executed2", false)) {
                    Intent in=new Intent(Loginactivity.this,MainActivity.class);
                    startActivity(in);
                    finish();
                } else {
                    Intent in=new Intent(Loginactivity.this,HospitalLogin.class);
                    startActivity(in);
                    finish();
                }

            }
        });
    }

}