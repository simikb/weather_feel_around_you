package com.qc.ssm.ifc.feelclimate.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.qc.ssm.ifc.feelclimate.databinding.ActivityStartBinding;
import com.qc.ssm.ifc.feelclimate.utils.Permissions;

public class StartActivity extends AppCompatActivity {
    ActivityStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.searchButton.setOnClickListener(view -> {
            if (!Permissions.checkPermission(StartActivity.this)) {
                Permissions.requestPermission(StartActivity.this, 1);
            }
            else if (Permissions.checkPermission(StartActivity.this))
            {startActivity(new Intent(StartActivity.this, MainActivity.class));}
            else{
                Snackbar.make(getApplicationContext(),this,"Something went wrong").show();
            }
        });
    }

}

