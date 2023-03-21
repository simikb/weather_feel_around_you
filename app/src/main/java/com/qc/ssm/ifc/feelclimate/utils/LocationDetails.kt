package com.qc.ssm.ifc.feelclimate.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import java.util.*

class LocationDetails(var context: Context) {
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mSettingsClient: SettingsClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var mLocationSettingsRequest: LocationSettingsRequest? = null
    private var mLocationCallback: LocationCallback? = null

    //saved location
    companion object {
        var mCurrentLocation: Location? = null
    }

    private var mRequestingLocationUpdates: Boolean? = null
    private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000

    fun initCall() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        mSettingsClient = LocationServices.getSettingsClient(context)

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                // location is received
                mCurrentLocation = locationResult.lastLocation
                PreferenceData.init(context);
                if (mCurrentLocation != null) {
                    mCurrentLocation?.longitude?.toLong()
                        ?.let {
                            mCurrentLocation?.latitude?.toLong()
                                ?.let { it1 -> PreferenceData.writeLoc(it1, it) }
                        }

                }
            }
        }

        mRequestingLocationUpdates = false
        mLocationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(500)
            .setMaxUpdateDelayMillis(1000)
            .build()
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        mLocationSettingsRequest = builder.build()

        mRequestingLocationUpdates = true
        if (mRequestingLocationUpdates!! && checkPermissions()) {
            startLocationUpdates()
        }
    }


    fun stopLocationUpdates() {
        // Removing location updates
        mRequestingLocationUpdates = false
        mLocationCallback?.let {
            mFusedLocationClient!!
                .removeLocationUpdates(it)
                .addOnCompleteListener(context as Activity) {

                }
        }
    }

    private fun startLocationUpdates() {
        mLocationSettingsRequest?.let {
            mSettingsClient!!
                .checkLocationSettings(it)
                .addOnSuccessListener(context as Activity) {

                    //Toast.makeText(context, "Started location updates!", Toast.LENGTH_SHORT).show()
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return@addOnSuccessListener
                    }
                    mLocationRequest?.let {
                        mLocationCallback?.let { it1 ->
                            mFusedLocationClient!!.requestLocationUpdates(
                                it,
                                it1, Looper.myLooper()
                            )
                        }
                    }

                }
                .addOnFailureListener(context as Activity) { e ->
                    when ((e as ApiException).statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {

                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the
                                // result in onActivityResult().
                                val rae = e as ResolvableApiException
                                rae.startResolutionForResult(
                                    context.applicationContext as Activity,
                                    100
                                )
                            } catch (sie: IntentSender.SendIntentException) {
                                Log.i("exception", "PendingIntent unable to execute request.")
                            }
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            val errorMessage = "Location settings are inadequate, and cannot be " +
                                    "fixed here. Fix in Settings."
                            Log.e("error", errorMessage)
                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }

                }
        }
    }

    public fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val permissionCoarseState = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED || permissionCoarseState == PackageManager.PERMISSION_GRANTED
    }

    fun getLat(): Long? {
        PreferenceData.init(context);
        return PreferenceData.readLat();
    }

    fun getLong(): Long? {
        PreferenceData.init(context);
        return PreferenceData.readLong();
    }
}