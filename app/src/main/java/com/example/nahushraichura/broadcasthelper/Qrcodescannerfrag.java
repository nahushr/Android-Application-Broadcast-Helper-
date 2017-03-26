package com.example.nahushraichura.broadcasthelper;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

public  class Qrcodescannerfrag extends Fragment implements QRCodeReaderView.OnQRCodeReadListener {

    //private IntentIntegrator qrScan;
    private QRCodeReaderView mydecoderview;
    String contents;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.qrcodereader, container, false);

        mydecoderview = (QRCodeReaderView) view.findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);

        // Use this function to enable/disable decoding
        mydecoderview.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        mydecoderview.setAutofocusInterval(2000L);

        // Use this function to enable/disable Torch
        mydecoderview.setTorchEnabled(true);

        // Use this function to set front camera preview
        mydecoderview.setFrontCamera();

        // Use this function to set back camera preview
        mydecoderview.setBackCamera();

        return view;
    }
    @Override
    public void onQRCodeRead(String text, PointF[] points) {

        String userdata[]=new String[6];
        contents = text;
        try {
            Log.d("contens",contents);
            String line=new Retrieveuserdata(getContext(), 0).execute(contents).get();
            Log.d("qr_scan_string ",line);
            line=line.trim();
            userdata=line.split(",");

        }
        catch(Exception e) {
            e.printStackTrace();
        }

        try
        {
//name,email,mobile,dob,age,qr
            Log.d("userdata0",userdata[0]);
            Log.d("userdata1",userdata[1]);
            Log.d("userdata2",userdata[2]);
            Log.d("userdata3",userdata[3]);
            Log.d("userdata4",userdata[4]);
            Log.d("userdata5",userdata[5]);

            userdata[0]=userdata[0].trim();
            userdata[1]=userdata[1].trim();
            userdata[2]=userdata[2].trim();
            userdata[3]=userdata[3].trim();
            userdata[4]=userdata[4].trim();
            userdata[5]=userdata[5].trim();

            String line=new FinalRegistration(getContext(), 0).execute(userdata[0],userdata[1],userdata[2],userdata[3],userdata[4],userdata[5]).get();
            Log.d("qr_scan_confirm",line);
            line=line.trim();
            if(line.equalsIgnoreCase("done")) {
                      Toast.makeText(getActivity(),"registration done",Toast.LENGTH_LONG).show();
                Intent in=new Intent(getContext(),UserConfirm.class);
                in.putExtra("name",userdata[0]);
                in.putExtra("email",userdata[1]);
                in.putExtra("mobile",userdata[2]);
                in.putExtra("dob",userdata[3]);
                in.putExtra("age",userdata[4]);
                in.putExtra("qr",userdata[5]);

                startActivity(in);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        mydecoderview.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mydecoderview.stopCamera();
    }

}
