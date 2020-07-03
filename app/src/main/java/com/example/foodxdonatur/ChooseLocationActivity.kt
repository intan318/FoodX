package com.example.foodxdonatur

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.foodxdonatur.donasi.DonasiPresenter
import com.example.foodxdonatur.donasi.DonasiView
import com.example.foodxdonatur.model.Coordinate
import com.example.foodxdonatur.model.DonasiResponse
import com.example.foodxdonatur.model.LocationResponse
import com.example.foodxdonatur.model.MakananResponse
import com.example.foodxdonatur.utils.DialogView
import com.example.foodxdonatur.utils.GPSChecker
import com.example.foodxdonatur.utils.ProgressRequestBody
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.activity_choose_location.*
import java.lang.NullPointerException

class ChooseLocationActivity : AppCompatActivity(), OnMapReadyCallback, PlaceSelectionListener,  ProgressRequestBody.UploadCallBacks {

    private lateinit var dialogView: DialogView
    private lateinit var dialog: DialogInterface
    private lateinit var location: LatLng

    private var locationName: String? = null
    private var gMaps: GoogleMap? = null
    private lateinit var donasiPresenter: DonasiPresenter
    private val mapKey = "MapViewFoodX"

    val GPS_REQUEST_CODE = 200
    lateinit var coordinateGPS: Coordinate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_location)
        captureCoordinateGPS()

        dialogView = DialogView(this)
        location = LatLng(coordinateGPS.lat, coordinateGPS.lng)
        locationName = intent.getStringExtra("name")

        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(mapKey)
        }
        map.onCreate(mapViewBundle)
        map.getMapAsync(this)

        if (!Places.isInitialized()) Places.initialize(this, getString(R.string.MAP_API_KEY))

        val placeAutoCompleteFragment =
            supportFragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as AutocompleteSupportFragment

        placeAutoCompleteFragment.setPlaceFields(listOf(Place.Field.LAT_LNG, Place.Field.NAME))
        placeAutoCompleteFragment.setOnPlaceSelectedListener(this)

        donasiPresenter = DonasiPresenter(
            this,
            object : DonasiView {
                override fun onLoading() {
                    dialogView.showProgressDialog()
                }

                override fun onFinish() {
                    dialogView.hideProgressDialog()
                }

                override fun getResponses(success: DonasiResponse?) {

                }

                override fun getMakananResponse(success: MakananResponse?) {

                }

                override fun onLocationResult(
                    result: LocationResponse?,
                    originalLatLng: LatLng
                ) {

                    if (result != null) {
                        val placeName = result.results!![0].formattedAddress
                        onPlaceUpdated(originalLatLng, placeName.toString())
                    }
                }

                override fun error(error: String) {

                }

            }, this)
    }


    private fun captureCoordinateGPS() {
        val gpsTracker by lazy { GPSChecker(this, this) }

        coordinateGPS = gpsTracker.check()
        Log.e("lat,lng orginal", coordinateGPS.lat.toString() + "," + coordinateGPS.lng.toString())
    }

    override fun onMapReady(map: GoogleMap?) {
        gMaps = map
        gMaps?.setMinZoomPreference(11f)
        gMaps?.moveCamera(CameraUpdateFactory.newLatLng(location))
        val mainMarker = gMaps?.addMarker(
            MarkerOptions().position(location).title(locationName).icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)
            )
        )
        gMaps?.setOnMapClickListener {
            donasiPresenter.getLocation(it.latitude.toString(), it.longitude.toString())
        }

        btn_my_location.setOnClickListener {
            navigateMyLocation()
        }

        btn_choose_location.setOnClickListener {
            sendBackLocation()
        }


        mainMarker?.showInfoWindow()
    }

    override fun onPlaceSelected(place: Place) {
        try {
            location = place.latLng!!
            locationName = place.name
            gMaps?.clear()
            val mainMarker = gMaps?.addMarker(
                MarkerOptions().position(place.latLng!!).title(place.name).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)
                )
            )
            gMaps?.moveCamera(CameraUpdateFactory.newLatLng(place.latLng))
            gMaps?.animateCamera(CameraUpdateFactory.newLatLngZoom(place.latLng, 12.0f))
            mainMarker?.showInfoWindow()
        } catch (e: NullPointerException) {
            Log.e("Place", place.name + "+" + place.latLng)
        }
    }

    private fun onPlaceUpdated(place: LatLng, placeName: String) {
        location = place
        locationName = placeName
        gMaps?.clear()
        val mainMarker = gMaps?.addMarker(
            MarkerOptions().position(place).title(placeName).icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)
            )
        )
        gMaps?.moveCamera(CameraUpdateFactory.newLatLng(place))
        gMaps?.animateCamera(CameraUpdateFactory.newLatLngZoom(place, 11f))
        mainMarker?.showInfoWindow()
    }

    private fun navigateMyLocation() {
        val place = LatLng(coordinateGPS.lat, coordinateGPS.lng)
        location = place
        locationName = "Lokasi Anda Saat Ini"
        gMaps?.clear()
        val mainMarker = gMaps?.addMarker(
            MarkerOptions().position(place).title("Lokasi Anda Saat Ini").icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)
            )
        )
        gMaps?.moveCamera(CameraUpdateFactory.newLatLng(place))
        gMaps?.animateCamera(CameraUpdateFactory.newLatLngZoom(place, 11f))
        mainMarker?.showInfoWindow()
    }

    private fun sendBackLocation() {
        val intent = Intent()
        intent.putExtra("lat", location.latitude.toString())
        intent.putExtra("lng", location.longitude.toString())
        intent.putExtra("name", locationName)
        replyIntentResult(resultCode = Activity.RESULT_OK, intent = intent)
    }

    private fun replyIntentResult(intent: Intent, resultCode: Int) {
        setResult(resultCode, intent)
        finish()
    }

    override fun onError(p0: Status) {
    }

    override fun onProgressUpdate(percentage: Int) {

    }

    override fun onStart() {
        super.onStart()
        captureCoordinateGPS()
        map.onStart()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onStop() {
        super.onStop()
        map.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        map.onDestroy()
    }

}