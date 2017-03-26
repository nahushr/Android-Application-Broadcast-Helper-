package com.example.nahushraichura.broadcasthelper;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SendPushNotification  extends Fragment{

    EditText pushmessage;
    TextView pushstatus;
    Button sendbutton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sendpush, container, false);
        final ProgressDialog mydialog=new ProgressDialog(getContext());
        mydialog.setMessage("Sending Message...");
        mydialog.setProgressStyle(R.style.myDialog);
        pushmessage=(EditText)view.findViewById(R.id.pushnotify);
        pushstatus=(TextView)view.findViewById(R.id.pushstatus);
        sendbutton=(Button)view.findViewById(R.id.sendbutton);

        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mydialog.show();
                try {
                    String line = new SendPushAsync(getContext(), 0).execute(pushmessage.getText().toString()).get();
                    Log.d("line string ", line);
                    line = line.trim();
                    pushstatus.setText("MESSAGE DELIVERED SUCCESSFULLY");
                    pushstatus.setTextColor(getResources().getColor(R.color.green));

                }
                catch (Exception e)
                {
                    pushstatus.setText("THERE WAS A PROBLEM SENDING THE MESSAGE");
                    pushstatus.setTextColor(getResources().getColor(R.color.red));
                    e.printStackTrace();
                }
                mydialog.dismiss();

            }
        });


        return view;
    }
}
