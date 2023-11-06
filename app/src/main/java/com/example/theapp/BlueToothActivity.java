package com.example.theapp;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.theapp.databinding.ActivityBlueToothBinding;
import com.example.theapp.databinding.ActivityMainBinding;

public class BlueToothActivity extends AppCompatActivity {

    private static final int REQUEST_BLUETOOTH_PERMISSION = 1;
    private BluetoothAdapter bluetoothAdapter;
    ActivityBlueToothBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityBlueToothBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {

            binding.bluetoothVerify.setText("Bluetooth not supported");
            finish();
        } else {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != android.content.pm.PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, REQUEST_BLUETOOTH_PERMISSION);
            } else {

                checkBluetoothStatus();
            }
        }
    }

    private void checkBluetoothStatus() {
        if (!bluetoothAdapter.isEnabled()) {

            Toast.makeText(this, "Please Enable the bluetooth", Toast.LENGTH_SHORT).show();

            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivityForResult(enableBluetoothIntent, 1);

        } else {

            binding.bluetoothVerify.setText("BlueTooth enabled");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_BLUETOOTH_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                checkBluetoothStatus();
            } else {

                Toast.makeText(this, "Bluetooth permission denied", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
