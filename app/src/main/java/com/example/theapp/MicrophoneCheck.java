package com.example.theapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

public class MicrophoneCheck extends AppCompatActivity {

    TextView microphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_microphone_check);
        microphone=findViewById(R.id.check_microphone);
        PackageManager packageManager = this.getPackageManager();
        if(packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE))
        {
            microphone.setText("Microphone Present");
        }
        else
        {
            microphone.setText("Microphone not Present");
        }
    }
}