package com.example.nahushraichura.broadcasthelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HospitalLogin extends AppCompatActivity {

    private EditText password;
    private Button confirmation;
    private TextInputLayout textInputLayout;
    //ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_login);

        //final String hospcode="H3";
        password= (EditText) findViewById(R.id.tv_pass);
        confirmation= (Button) findViewById(R.id.hosp_login);
        textInputLayout= (TextInputLayout) findViewById(R.id.hosp_tlv);
        textInputLayout.setHint("Username");









        //username= (EditText) findViewById(R.id.tv_user);

        confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {

                    Log.d("confirmation_click","true");
                    String line = new HospitalCodeCheck(getApplicationContext(), 0).execute(password.getText().toString()).get();
                    line=line.trim();


                    Log.d("hospital_login",line);
                    if(line.equalsIgnoreCase("gm"))
                    {


                        Toast.makeText(getApplicationContext(),"hospital code invalid",Toast.LENGTH_LONG).show();
                    }
                    else
                    {

                        //hospital code put to shared preference
                        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("HospitalCode",password.getText().toString());

                        //check activity execution
                        SharedPreferences pref2 = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edt = pref2.edit();
                        edt.putBoolean("activity_executed2", true);
                        edt.commit();

                        startActivity(new Intent(HospitalLogin.this,MainActivity.class));
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });


    }
}
