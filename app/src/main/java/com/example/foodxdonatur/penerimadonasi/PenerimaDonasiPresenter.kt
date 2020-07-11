package com.example.foodxdonatur.penerimadonasi

import android.content.Context
import android.util.Log
import com.example.foodxdonatur.R
import com.example.foodxdonatur.network.APIFactory
import com.example.foodxdonatur.network.APIServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class PenerimaDonasiPresenter(val context: Context, val view: PenerimaDonasiView) {

    private var service: APIServices = APIFactory.makeRetrofitService()
    var googleServiceRepos = APIFactory.makeGoogleService()
    var job: Job? = null

    fun getPenerimaDonasi(token: String?, lat: String, lng: String, radius: Int) {

        view.isLoading()
        GlobalScope.launch(Dispatchers.Main){
            try {
                val data = service.getPenerimaDonasi("Bearer $token", lat, lng, radius
                )
                val result = data.await()

                view.showPenerimaDonasi(result.body())
                view.stopLoading()

            } catch (e: Exception) {
                Log.e("ERROR", e.message.toString())
                view.stopLoading()
            }
        }
    }

    fun getLocation(lat: String, lng: String) {

        view.isLoading()
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val data = googleServiceRepos.getLocation(
                    "$lat, $lng",
                    context.getString(R.string.MAP_API_KEY)
                )

                val result = data.await()

                when {
                    result.code() == 500 -> {
                        view.onError("Server Error")
                    }
                    result.code() == 404 -> {
                        view.onError("Not Found")
                    }
                    else -> {
                        view.onLocationResult(result.body(), LatLng(lat.toDouble(), lng.toDouble()))
                    }
                }

                view.stopLoading()
            } catch (t: Throwable) {
                view.onError(t.printStackTrace().toString())
                view.stopLoading()
            }
        }
    }

}