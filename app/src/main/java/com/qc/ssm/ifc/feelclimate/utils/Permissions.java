package com.qc.ssm.ifc.feelclimate.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permissions {

    static final public String apiKey = "2d4f05278c6ccc9692977747b6cbe102";

    //  CHECK FOR LOCATION PERMISSION
    public static boolean checkPermission(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    //REQUEST FOR PERMISSSION
    public static void requestPermission(Activity activity, final int code) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            Toast.makeText(activity, "GPS permission allows us to access location data. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, code);
        }
    }

}
