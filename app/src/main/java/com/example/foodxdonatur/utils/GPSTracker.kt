package com.example.foodxdonatur.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class GPSTracker(private var context: Context? = null, private var activity: Context? = null) :
    Service() {

    // Flag for GPS status
    private var isGPSEnabled = false

    // Flag for network status
    private var isNetworkEnabled = false

    // Flag for GPS status
    private var canGetLocation = false


    val CODE_LOCATION = 200

    private var location: Location? = null // Location
    internal var latitude: Double = 0.toDouble() // Latitude
    internal var longitude: Double = 0.toDouble() // Longitude

    // Declaring a Location Manager
    private var locationManager: LocationManager? = null


    private val mLocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {

            if (location != null) {
                latitude = location.latitude
                longitude = location.longitude
            }
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

        }

        override fun onProviderEnabled(provider: String) {

        }

        override fun onProviderDisabled(provider: String) {

        }
    }

    @SuppressLint("MissingPermission")
    fun getLocation(): Location? {
        try {

            locationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager?

            // Getting GPS status
            isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)

            // Getting network status
            isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            when {
                !isGPSEnabled && !isNetworkEnabled -> {
                    // No network provider is enabled
                }
                else -> {
                    this.canGetLocation = true
                    when {
                        isNetworkEnabled -> {
                            val requestPermissionsCode = 50

                            locationManager!!.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(),
                                mLocationListener
                            )
                            Log.d("Network", "Network")
                            when {
                                locationManager != null -> {
                                    location =
                                        locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                                    if (location != null) {
                                        latitude = location!!.latitude
                                        longitude = location!!.longitude
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // If GPS enabled, get latitude/longitude using GPS Services
            when {
                isGPSEnabled -> if (location == null) {
                    when {
                        ContextCompat.checkSelfPermission(
                            context!!, Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            context!!,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED -> ActivityCompat.requestPermissions(
                            activity!! as Activity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            50
                        )
                        else -> {
                            locationManager?.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(),
                                mLocationListener
                            )
                            Log.d("GPS Enabled", "GPS Enabled")
                            when {
                                locationManager != null -> {

                                    location =
                                        locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                                    if (location != null) {
                                        latitude = location!!.latitude
                                        longitude = location!!.longitude
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return location
    }


    /**
     * Function to get latitude
     */
    fun getLatitude(): Double {
        if (location != null) {
            latitude = location!!.latitude
        }

        // return latitude
        return latitude
    }


    /**
     * Function to get longitude
     */
    fun getLongitude(): Double {
        if (location != null) {
            longitude = location!!.longitude
        }

        // return longitude
        return longitude
    }

    /**
     * Function to check GPS/Wi-Fi enabled
     * @return boolean
     */
    fun canGetLocation(): Boolean {
        return this.canGetLocation
    }


//    /**
//     * Function to show settings alert dialog.
//     * On pressing the Settings button it will launch Settings Options.
//     */
//    fun showSettingsAlert() {
//
//
//        (activity as BaseActivity).showDialogWithOutCancel(
//            context?.getString(R.string.gps_title).toString(),
//            context?.getString(R.string.gps_warning).toString(),
//            false,
//            listener = object : AlertDialogInterface {
//                override fun positiveButtonAction(dialog: DialogInterface, id: Int) {
//                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                    (activity as BaseActivity).startActivityForResult(intent, CODE_LOCATION)
//                }
//
//                override fun negativeButtonAction(dialog: DialogInterface, id: Int) {
//
//                }
//
//            }
//        )
//    }


    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    companion object {
        // The minimum distance to change Updates in meters
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 1000 // 10 meters

        // The minimum time between updates in milliseconds
        private const val MIN_TIME_BW_UPDATES = (1000 * 60 * 1).toLong() // 1 minute
    }
}