package com.example.foodxdonatur.penerimadonasi

import android.util.Log
import com.example.foodxdonatur.model.KomunitasResponse
import com.example.foodxdonatur.model.LocationResponse
import com.example.foodxdonatur.model.PenerimaDonasiResponse
import com.google.android.gms.maps.model.LatLng

interface PenerimaDonasiView {

    fun isLoading()
    fun stopLoading()
    fun showPenerimaDonasi(data: PenerimaDonasiResponse?)
    fun onLocationResult(result: LocationResponse?, originalLatLng: LatLng)
    fun error (error : String)
    fun onError(error : String){
        Log.e("Error", error)
    }

}