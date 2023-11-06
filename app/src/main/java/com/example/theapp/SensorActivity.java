package com.example.theapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity {

    private TextView sensorStatusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        sensorStatusTextView = findViewById(R.id.sensorStatusTextView);
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager != null) {

            checkSensorAvailability(sensorManager, Sensor.TYPE_ACCELEROMETER, "Accelerometer");


            checkSensorAvailability(sensorManager, Sensor.TYPE_GYROSCOPE, "Gyroscope");


            checkSensorAvailability(sensorManager, Sensor.TYPE_LIGHT, "Light Sensor");


            checkSensorAvailability(sensorManager, Sensor.TYPE_PROXIMITY, "Proximity Sensor");


            checkSensorAvailability(sensorManager, Sensor.TYPE_MAGNETIC_FIELD, "Magnetic Field Sensor");


            checkSensorAvailability(sensorManager, Sensor.TYPE_PRESSURE, "Pressure Sensor");


            checkSensorAvailability(sensorManager, Sensor.TYPE_ROTATION_VECTOR, "Rotation Vector Sensor");


            checkSensorAvailability(sensorManager, Sensor.TYPE_STEP_COUNTER, "Step Counter Sensor");


            checkSensorAvailability(sensorManager, Sensor.TYPE_HEART_RATE, "Heart Rate Sensor");


        } else {
            sensorStatusTextView.setText("Sensor service not supported on this device.");
        }
    }

    private void checkSensorAvailability(SensorManager sensorManager, int sensorType, String sensorName) {
        Sensor sensor = sensorManager.getDefaultSensor(sensorType);
        if (sensor != null) {
            sensorStatusTextView.append(sensorName + " sensor is available on this device.\n");
        } else {
            sensorStatusTextView.append(sensorName + " sensor is not available on this device.\n");
        }
    }
}