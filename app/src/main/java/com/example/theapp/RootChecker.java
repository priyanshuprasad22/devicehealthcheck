package com.example.theapp;

public class RootChecker {
    public static boolean isDeviceRooted() {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }
}
