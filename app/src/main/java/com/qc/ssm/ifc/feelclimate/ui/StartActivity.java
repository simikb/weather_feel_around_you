package com.qc.ssm.ifc.feelclimate.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.qc.ssm.ifc.feelclimate.databinding.ActivityStartBinding;
import com.qc.ssm.ifc.feelclimate.utils.LocationDetails;
import com.qc.ssm.ifc.feelclimate.utils.Permissions;
import com.qc.ssm.ifc.feelclimate.utils.Utils;


import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StartActivity extends AppCompatActivity {

    ActivityStartBinding binding;
    LocationDetails locationDetails;
    int requestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        locationDetails = new LocationDetails(StartActivity.this);
        if (!Permissions.checkPermission(StartActivity.this)) {
            Permissions.requestPermission(StartActivity.this, requestCode);
        }
        binding.searchButton.setOnClickListener(view -> {
            goToNext();
        });

        saveLocation();
        getLastStatus();

    }

    private void getLastStatus() {
        String lastCity = new Utils().getLastStatus(StartActivity.this);
        if (lastCity != null) {
            goToNext();
            finish();
        }
    }

    private void saveLocation() {
        locationDetails.initCall();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void goToNext() {
        //viewModel.setStateEvent(MainStateEvent.GetClimateEvents);
        startActivity(new Intent(StartActivity.this, SearchActivity.class));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == this.requestCode && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            goToNext();
        } else {
            Toast.makeText(StartActivity.this, "", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationDetails.stopLocationUpdates();
    }
}

