package com.example.nahushraichura.broadcasthelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by VaibhavShah on 26/03/17.
 */
public class ActivityXPTO extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation);


        String idOffer = "";

        Intent startingIntent = getIntent();
        if (startingIntent != null) {
            idOffer = startingIntent.getStringExtra("id_offer"); // Retrieve the id
        }

        //getOfferDetails(id_offer);
    }

}