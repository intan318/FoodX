package com.example.foodxdonatur.history

import android.content.Context
import android.util.Log
import com.example.foodxdonatur.network.APIFactory
import com.example.foodxdonatur.network.APIServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class HistoryDonasiPresenter(val context: Context, val view: HistoryDonasiView) {

    private var service: APIServices = APIFactory.makeRetrofitService()
    var job: Job? = null

    fun getHistoryDonasi(
        token: String?,
        donasiId: String? = null,
        komunitasId: String? = null,
        relawanId: String? = null,
        penerimaId: String? = null,
        alamatPenjemputan: String? = null,
        status: String? = null,
        tglPenjemputan: String? = null,
        waktuPenjemputan: String? = null,
        latitude: String? = null,
        longitude: String? = null,
        notes: String? = null,
        foto: String? = null
    ) {
        view.isLoading()

        GlobalScope.launch(Dispatchers.Main){
            try {
                val data = service.getHistoryDonasi(
                    token = "Bearer $token",
                    donasiId = donasiId,
                    komunitasId = komunitasId,
                    relawanId = relawanId,
                    penerimaId = penerimaId,
                    alamatPenjemputan = alamatPenjemputan,
                    status = status,
                    tglPenjemputan = tglPenjemputan,
                    waktuPenjemputan = waktuPenjemputan,
                    latitude = latitude,
                    longitude = longitude,
                    notes = notes,
                    foto = foto
                )

                val result = data.await()

                view.getResponses(result.body())
                view.stopLoading()

            } catch (e: Exception) {
                Log.e("ERROR", e.message.toString())
                view.stopLoading()
            }
        }
    }
}