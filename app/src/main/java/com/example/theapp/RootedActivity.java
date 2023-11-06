package com.example.theapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.theapp.databinding.ActivityRootedBinding;

public class RootedActivity extends AppCompatActivity {

    ActivityRootedBinding rootedBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootedBinding = ActivityRootedBinding.inflate(getLayoutInflater());
        setContentView(rootedBinding.getRoot());

        boolean isRooted = RootChecker.isDeviceRooted();
        if (isRooted) {
            rootedBinding.rootCheck.setText("The Device is Rooted");
        } else
        {
            rootedBinding.rootCheck.setText("The Device is Not Rooted");
        }

    }
}