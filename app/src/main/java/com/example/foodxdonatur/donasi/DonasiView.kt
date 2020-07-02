package com.example.foodxdonatur.donasi

import android.util.Log
import com.example.foodxdonatur.model.DonasiResponse
import com.example.foodxdonatur.model.LocationResponse
import com.example.foodxdonatur.model.MakananResponse
import com.example.foodxdonatur.model.RegisterResponse
import com.google.android.gms.maps.model.LatLng

interface DonasiView {

    fun onLoading()
    fun onFinish()
    fun getResponses(success : DonasiResponse?)
    fun getMakananResponse(success: MakananResponse?)
    fun onLocationResult(result: LocationResponse?, originalLatLng: LatLng)
    fun error (error : String)
    fun onError(error : String){
        Log.e("Error", error)
    }

}