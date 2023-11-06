package com.example.theapp;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.hardware.camera2.CameraAccessException;
//import android.hardware.camera2.CameraCharacteristics;
//import android.hardware.camera2.CameraManager;
//import android.os.Build;
//
//public class CameraUtil {
//
//    public static boolean hasFrontCamera(Context context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
//            try {
//                for (String cameraId : manager.getCameraIdList()) {
//                    CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
//                    int facing = characteristics.get(CameraCharacteristics.LENS_FACING);
//                    if (facing == CameraCharacteristics.LENS_FACING_FRONT) {
//                        return true;
//                    }
//                }
//            } catch (CameraAccessException e) {
//                e.printStackTrace();
//            }
//        } else {
//            // For older devices, check if the device has a front camera
//            return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
//        }
//        return false;
//    }
//
//    public static boolean hasBackCamera(Context context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
//            try {
//                for (String cameraId : manager.getCameraIdList()) {
//                    CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
//                    int facing = characteristics.get(CameraCharacteristics.LENS_FACING);
//                    if (facing == CameraCharacteristics.LENS_FACING_BACK) {
//                        return true;
//                    }
//                }
//            } catch (CameraAccessException e) {
//                e.printStackTrace();
//            }
//        } else {
//            return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
//        }
//        return false;
//    }
//}

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Executors;

public class CameraUtil {

    private final Context context;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private CameraVerificationListener verificationListener;

    public interface CameraVerificationListener {
        void onCameraVerified(boolean isFrontCamera);
        void onVerificationFailed();
    }

    public CameraUtil(Context context) {
        this.context = context;
    }

    public void setCameraVerificationListener(CameraVerificationListener listener) {
        this.verificationListener = listener;
    }

    public void startCamera(LifecycleOwner lifecycleOwner, Preview.SurfaceProvider surfaceProvider, int lensFacing) {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(context);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(lensFacing)
                        .build();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(surfaceProvider);

                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();
                imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(), imageProxy -> {
                    // Analyze the camera frames here if needed
                    imageProxy.close();
                });

                cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalysis);

                if (verificationListener != null) {
                    mainHandler.post(() -> {
                        verificationListener.onCameraVerified(lensFacing == CameraSelector.LENS_FACING_FRONT);
                    });
                }

            } catch (Exception e) {
                if (verificationListener != null) {
                    mainHandler.post(() -> {
                        verificationListener.onVerificationFailed();
                    });
                }
            }
        }, ContextCompat.getMainExecutor(context));
    }
}

