package com.example.nahushraichura.broadcasthelper;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserConfirm extends AppCompatActivity {

    private EditText name,mobile,age,DOB,email;
    private Button confirm;
    private TextInputLayout tname,temail,tmobile,tage,tDOB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_confirm);

        Toast.makeText(getApplicationContext(),"User Registered",Toast.LENGTH_LONG).show();


        name= (EditText) findViewById(R.id.tv_name);
        mobile= (EditText) findViewById(R.id.tv_mobile);
        age= (EditText) findViewById(R.id.tv_age);
        DOB= (EditText) findViewById(R.id.tv_DOB);
        email= (EditText) findViewById(R.id.tv_email);
        confirm= (Button) findViewById(R.id.bt_confirm);
        tname= (TextInputLayout) findViewById(R.id.tlv_name);
        temail= (TextInputLayout) findViewById(R.id.tlv_email);
        tmobile= (TextInputLayout) findViewById(R.id.tlv_mobile);
        tage= (TextInputLayout) findViewById(R.id.tlv_age);
        tDOB= (TextInputLayout) findViewById(R.id.tlv_dob);
        tname.setHint("name");
        tmobile.setHint("mobile");
        tage.setHint("age");
        temail.setHint("email");
        tDOB.setHint("date of birth");


        name.setText(getIntent().getStringExtra("name"));
        email.setText(getIntent().getStringExtra("email"));
        DOB.setText(getIntent().getStringExtra("dob"));
        mobile.setText(getIntent().getStringExtra("mobile"));
        age.setText(getIntent().getStringExtra("age"));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on confirmation of user details
                finish();


            }
        });

    }
}
