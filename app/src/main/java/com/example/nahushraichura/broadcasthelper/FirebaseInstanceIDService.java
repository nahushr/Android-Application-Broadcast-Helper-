package com.example.nahushraichura.broadcasthelper;

import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by NAHUSH RAICHURA on 3/24/2017.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh()
    {
        String token=FirebaseInstanceId.getInstance().getToken();
        registerToken(token);
    }
    private void registerToken(String token)
    {
        //send token to database
        try {
            String line = new TokenRegisteringActivity(this, 0).execute(token).get();
            line=line.trim();
            if(line.equalsIgnoreCase("token already registered"))
            {
                Toast.makeText(this,"token already registered",Toast.LENGTH_LONG).show();
            }
            if(line.equalsIgnoreCase("token registered"))
            {
                Toast.makeText(this,"token successfully registered",Toast.LENGTH_LONG).show();
            }
            if(line.equalsIgnoreCase("token registration failed"))
            {
                Toast.makeText(this,"token registration failed",Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
