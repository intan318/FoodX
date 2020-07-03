package com.example.foodxdonatur.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.foodxdonatur.model.Coordinate

class GPSChecker(
    val context: Context,
    val activity: Activity,
    private val container: View? = null
) {

    fun check(): Coordinate {

        //checkingConnection()
        var lat = 0.0
        var lng = 0.0
        when {

            ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED -> ActivityCompat.requestPermissions(
                activity as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )

            else -> {
                //Snackbar.make(container, "You need have granted Manifest.permission", Snackbar.LENGTH_SHORT).show()
                val gps = GPSTracker(context, activity)
                gps.getLocation()
                when {
                    gps.canGetLocation() -> {
                        lat = gps.getLatitude()
                        lng = gps.getLongitude()
                        //Snackbar.make(container, "coordinate captured", Snackbar.LENGTH_SHORT).show()
                    }
                    else -> gps.showSettingsAlert()
                }
            }
        }

        return Coordinate(lat, lng)
    }


}