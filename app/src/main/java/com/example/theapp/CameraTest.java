
package com.example.theapp;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import com.example.theapp.databinding.ActivityCameraTestBinding;

public class CameraTest extends AppCompatActivity implements CameraUtil.CameraVerificationListener {

    ActivityCameraTestBinding cameraTestBinding;
    private CameraUtil cameraUtil;
    private Preview.SurfaceProvider surfaceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraTestBinding = ActivityCameraTestBinding.inflate(getLayoutInflater());
        setContentView(cameraTestBinding.getRoot());

        cameraUtil = new CameraUtil(this);
        surfaceProvider = cameraTestBinding.previewView.createSurfaceProvider();

        cameraUtil.setCameraVerificationListener(this);

        cameraTestBinding.btnVerifyFrontCamera.setOnClickListener(view -> {
            cameraUtil.startCamera(this, surfaceProvider, CameraSelector.LENS_FACING_FRONT);
            showToast("Verifying front camera...");
        });

        cameraTestBinding.btnVerifyBackCamera.setOnClickListener(view -> {
            cameraUtil.startCamera(this, surfaceProvider, CameraSelector.LENS_FACING_BACK);
            showToast("Verifying back camera...");
        });
    }

    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onCameraVerified(boolean isFrontCamera) {
        showToast("Camera verified successfully. Front Camera: " + isFrontCamera);
    }

    @Override
    public void onVerificationFailed() {
        showToast("Camera verification failed!");
    }
}
