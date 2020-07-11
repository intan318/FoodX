package com.example.foodxdonatur.penerimadonasi

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AndroidRuntimeException
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.foodxdonatur.R
import com.example.foodxdonatur.model.LocationResponse
import com.example.foodxdonatur.model.PenerimaDonasiResponse
import com.example.foodxdonatur.utils.DialogView
import com.example.foodxdonatur.utils.SessionManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_detail_map_penerima.*
import kotlinx.android.synthetic.main.fragment_penerima_donasi.*

class DetailMapPenerimaActivity : AppCompatActivity(), OnMapReadyCallback, PenerimaDonasiView {

    private lateinit var dialogView: DialogView
    private lateinit var latLng: LatLng
    private var locationName: String? = null
    private val mapKey = "MAP_DETAIL_KEY"
    private lateinit var penerimaDonasiPresenter: PenerimaDonasiPresenter
//    private var userCovid: MutableList<User> = mutableListOf()
    private var penerimaDonasi: MutableList<PenerimaDonasiResponse.Penerimadonasi> = mutableListOf()
    private var gMaps: GoogleMap? = null

    private var previousMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_map_penerima)

        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(mapKey)
        }

        map_detail_penerima.onCreate(mapViewBundle)
        penerimaDonasiPresenter = PenerimaDonasiPresenter(this, this)

        val lat = intent.getStringExtra("lat")
        val lng = intent.getStringExtra("lng")
        locationName = intent.getStringExtra("name")
        btn_map_back.setOnClickListener {
            onBackPressed()
        }

        try {
            val token = SessionManager.getInstance(this).getToken()
            latLng = LatLng(lat!!.toDouble(), lng!!.toDouble())
            penerimaDonasiPresenter.getPenerimaDonasi(token!!, lat, lng, 10)
        } catch (e: NullPointerException) {

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


    override fun onMapReady(map: GoogleMap?) {
        try {
            gMaps = map
            gMaps?.setMinZoomPreference(11f)
            gMaps?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            val mainMarker = gMaps?.addMarker(
                MarkerOptions().position(latLng).title(locationName).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            gMaps?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            gMaps?.setOnMapClickListener {
                penerimaDonasiPresenter.getLocation(it.latitude.toString(), it.longitude.toString())
            }
            mainMarker?.showInfoWindow()

            penerimaDonasi.forEach {
                val latLongDest =  LatLng(it.latitude!!.toDouble(), it.longitude!!.toDouble())
                gMaps?.addMarker(
                    MarkerOptions().position(latLongDest)
                        .title(it.namaPenerima)
                        .icon(
                            bitmapDescriptorFromVector(
                                this,
                                R.drawable.marker_sehat
                            )
                        )
                )
            }
        } catch (e: Throwable) {
            Log.e("Error", e.message.toString())
        } catch (e: AndroidRuntimeException) {
            Log.e("Error", e.message.toString())
        }
    }

    override fun isLoading() {
        dialogView.showProgressDialog()
    }

    override fun stopLoading() {
        dialogView.hideProgressDialog()
    }

    override fun showPenerimaDonasi(data: PenerimaDonasiResponse?) {
        penerimaDonasi.addAll(data!!.penerimadonasi)

        if (data.penerimadonasi.isNotEmpty()) {
            penerimaDonasi.addAll(data.penerimadonasi)
        }
//        map_detail_penerima.getMapAsync(this)


    }

    override fun onLocationResult(result: LocationResponse?, originalLatLng: LatLng) {
        if (result != null){
            val placeName = result.results!![0].formattedAddress
            onKnowDetailPlace(originalLatLng, placeName.toString())
        }

    }

    private fun onKnowDetailPlace(place: LatLng, placeName: String) {
        if (previousMarker != null) previousMarker?.remove()
        previousMarker = gMaps?.addMarker(
            MarkerOptions().position(place).title(placeName).icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)
            )
        )
        gMaps?.moveCamera(CameraUpdateFactory.newLatLng(place))
        gMaps?.animateCamera(CameraUpdateFactory.newLatLngZoom(place, 11f))
        previousMarker?.showInfoWindow()
    }

    override fun error(error: String) {

    }

    override fun onStart() {
        super.onStart()
        map_detail_penerima.onStart()
    }

    override fun onPause() {
        super.onPause()
        map_detail_penerima.onPause()
    }

    override fun onStop() {
        super.onStop()
        map_detail_penerima.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map_detail_penerima.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        map_detail_penerima.onDestroy()
    }

}