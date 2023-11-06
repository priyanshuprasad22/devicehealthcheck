package com.example.theapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.theapp.databinding.ActivityHealthBinding;

public class HealthActivity extends AppCompatActivity {

    ActivityHealthBinding activityHealthBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHealthBinding = ActivityHealthBinding.inflate(getLayoutInflater());
        setContentView(activityHealthBinding.getRoot());

        activityHealthBinding.btnCameraTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HealthActivity.this,CameraTest.class);
                startActivity(intent);
            }
        });

        activityHealthBinding.btnBluetoothTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HealthActivity.this,BlueToothActivity.class);
                startActivity(intent);
            }
        });

        activityHealthBinding.btnSensorTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HealthActivity.this,SensorActivity.class);
                startActivity(intent);
            }
        });

        activityHealthBinding.btnMicrphoneTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HealthActivity.this,MicrophoneCheck.class);
                startActivity(intent);
            }
        });

        activityHealthBinding.btnRootedTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HealthActivity.this,RootedActivity.class);
                startActivity(intent);
            }
        });
    }
}