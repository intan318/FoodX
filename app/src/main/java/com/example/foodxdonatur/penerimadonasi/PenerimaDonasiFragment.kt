package com.example.foodxdonatur.penerimadonasi

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.AndroidRuntimeException
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.foodxdonatur.MainActivity
import com.example.foodxdonatur.R
import com.example.foodxdonatur.model.Coordinate
import com.example.foodxdonatur.model.LocationResponse
import com.example.foodxdonatur.model.PenerimaDonasiResponse
import com.example.foodxdonatur.utils.DialogView
import com.example.foodxdonatur.utils.GPSChecker
import com.example.foodxdonatur.utils.SessionManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_choose_location.*
import kotlinx.android.synthetic.main.fragment_penerima_donasi.*
import org.jetbrains.anko.internals.AnkoInternals.createIntent
import org.jetbrains.anko.support.v4.startActivity

class PenerimaDonasiFragment : Fragment(), OnMapReadyCallback, PenerimaDonasiView {

    private lateinit var penerimaDonasiPresenter: PenerimaDonasiPresenter
    private lateinit var dialogView: DialogView
    private lateinit var dialog: DialogInterface
    private var penerimaDonasi: MutableList<PenerimaDonasiResponse.Penerimadonasi> = mutableListOf()


    private val mapKey = "MapViewFoodX"
    private lateinit var latLong: LatLng
    private var locationTitle: String? = null
    private var token: String? = null

    lateinit var coordinateGPS: Coordinate


    val GPS_CODE_REQUEST = 200
    val CODE_CHOOSE_LOCATION = 2001

    private var myMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_penerima_donasi, container, false)
    }

//    private inline fun <reified T : Any> startActivityWithIntent(vararg params: Pair<String, Any?>) {
//        startActivity(createIntent(context!!, T::class.java, params))
//        overridePendingTransition(
//            R.anim.slide_in_right,
//            R.anim.slide_out_left
//        )
//
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        captureCoordinateGPS()

        dialogView = DialogView(activity!!)
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(mapKey)
        }

        map_dashboard.onCreate(mapViewBundle)
        locationTitle = "Lokasi Anda"
//        txt_location.text = locationTitle
        latLong = LatLng(coordinateGPS.lat, coordinateGPS.lng)

        penerimaDonasiPresenter = PenerimaDonasiPresenter(context!!, this)
        penerimaDonasiPresenter.getPenerimaDonasi(
            token = SessionManager.getInstance(context!!).getToken()!!,
            lat = latLong.latitude.toString(),
            lng = latLong.longitude.toString(),
            radius = 20)
        Log.e("Coordinate", latLong.toString())

    }

    @SuppressLint("DefaultLocale")
    override fun onMapReady(map: GoogleMap?) {
        try {
            Log.e("Errorrrrr", map.toString())
            myMap = map
            myMap?.uiSettings?.isZoomGesturesEnabled = false
            myMap?.uiSettings?.isScrollGesturesEnabledDuringRotateOrZoom = false
            myMap?.uiSettings?.setAllGesturesEnabled(false)
            myMap?.setMinZoomPreference(11f)
            myMap?.setOnMapClickListener {
                startActivity<DetailMapPenerimaActivity>(
                    "lat" to latLong.latitude.toString(),
                    "lng" to latLong.longitude.toString(),
                    "name" to locationTitle
                )
            }
            myMap?.moveCamera(CameraUpdateFactory.newLatLng(latLong))
            val mainMarker = myMap?.addMarker(
                MarkerOptions().position(latLong).title(locationTitle).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            mainMarker?.showInfoWindow()


            penerimaDonasi.forEach {
                val latLongDest =
                    LatLng(it.latitude!!.toDouble(), it.longitude!!.toDouble())
                myMap?.addMarker(MarkerOptions().position(latLongDest).title(it.namaPenerima).icon(
                    bitmapDescriptorFromVector(
                        context!!,
                        R.drawable.marker_sehat
                    )
                ))
            }

        } catch (e: Throwable) {
            Log.e("Error", e.message.toString())
        } catch (e: AndroidRuntimeException){
            Log.e("Error", e.message.toString())
        }

    }

    fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            val height = 50
            val width = 50
            val b: Bitmap = this.toBitmap()
            val smallMarker = Bitmap.createScaledBitmap(b, width, height, false)
            BitmapDescriptorFactory.fromBitmap(smallMarker)
        }
    }

    fun captureCoordinateGPS() {
        val gpsTracker by lazy { GPSChecker(context!!, activity!!) }

        coordinateGPS = gpsTracker.check()
        Log.e("lat,lng orginal", coordinateGPS.lat.toString() + "," + coordinateGPS.lng.toString())
    }

    override fun onStart() {
        super.onStart()
        captureCoordinateGPS()
        map_dashboard.onStart()
    }

    override fun onResume() {
        super.onResume()
        captureCoordinateGPS()
    }

    override fun onPause() {
        super.onPause()
        map_dashboard.onPause()
    }

    override fun onStop() {
        super.onStop()
        map_dashboard.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map_dashboard.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
//        map_dashboard.onDestroy()
    }

    override fun isLoading() {
        dialogView.showProgressDialog()
    }

    override fun stopLoading() {
        dialogView.hideProgressDialog()

    }

    @SuppressLint("SetTextI18n")
    override fun showPenerimaDonasi(data: PenerimaDonasiResponse?) {
        penerimaDonasi.addAll(data!!.penerimadonasi)

        if (data.penerimadonasi.isNotEmpty()) {
            penerimaDonasi.addAll(data.penerimadonasi)
        }

        Log.e("Error", data.toString())
     map_dashboard.getMapAsync(this)
    }

    override fun onLocationResult(result: LocationResponse?, originalLatLng: LatLng) {

    }

    override fun error(error: String) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_CHOOSE_LOCATION && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                try {
                    val lat = data.getStringExtra("lat")
                    val lng = data.getStringExtra("lng")
                    val name = data.getStringExtra("name")!!
                    latLong = LatLng(lat!!.toDouble(), lng!!.toDouble())
                    locationTitle = name
                } catch (e: NullPointerException) {
                    Toast.makeText(context, "Error filter", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}