package com.example.foodxdonatur.donasi

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import com.example.foodxdonatur.R
import com.example.foodxdonatur.network.APIFactory
import com.example.foodxdonatur.utils.ImageController
import com.example.foodxdonatur.utils.ProgressRequestBody
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception

class DonasiPresenter(
    val context: Activity,
    var view: DonasiView,
    callback: ProgressRequestBody.UploadCallBacks?
) {

    private var imageController = ImageController(context, callBackProgress = callback!!)
    private val service = APIFactory.makeRetrofitService()
    var googleServiceRepos = APIFactory.makeGoogleService()

    var job: Job? = null

    fun insertDonasi(
        token: String?,
        param: HashMap<String, RequestBody?>,
        file: MultipartBody.Part?
//        datas: List<HashMap<String, String?>>
    ) {
        view.onLoading()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val data = service.createDonasi(
                    token = "Bearer $token",
                    data = param,
                    foto = file
                )
                val result = data.await()


                view?.getResponses(result.body())
                view?.onFinish()


            } catch (e: Exception) {
                view?.onFinish()
                Log.e("ERROR", e.message.toString())
                view?.error(e.message.toString())
            }
        }
    }


//    @SuppressLint("SimpleDateFormat")
//    fun donasi(alamatPenjemputan: String, donaturId: String, foto: String, komunitasId: String,
//               latitude: String, longitude: String, notes: String, waktuPenjemputan: String,
//               bau: String, berubahrasa: String, berubahtekstur: String, berwarna: String,
//               jamur: String, jumlah: String, makananId: String, tglKadaluwarsa: String,
//               tglProduksi: String, unit: String) {
//
//        view?.onLoading()
//
//        doAsync {
//            try {
//                runBlocking {
//                    launch(Dispatchers.IO) {
//                        val data = service.createDonasi(alamatPenjemputan, donaturId, foto, komunitasId,
//                            latitude, longitude, notes, waktuPenjemputan, bau, berubahrasa, berubahtekstur,
//                            berwarna, jamur, jumlah, makananId, tglKadaluwarsa, tglProduksi, unit)
//                        val result = data.await()
//
//                        uiThread {
//                            view?.getResponses(result.body())
//                            view?.onFinish()
//                        }
//                    }
//                }
//            }
//            catch (e: Exception){
//                uiThread {
//                    view?.onFinish()
//                    view?.error(e.message.toString())
//                }
//            }
//        }
//    }

    @SuppressLint("SimpleDateFormat")
    fun makanan() {

        view?.onLoading()

        doAsync {
            try {
                runBlocking {
                    launch(Dispatchers.IO) {
                        val dataNamaMakanan = service.getMakanan()
                        val resultNamaMakanan = dataNamaMakanan.await()

                        uiThread {
                            view?.getMakananResponse(resultNamaMakanan.body())
                            view?.onFinish()
                        }
                    }
                }
            } catch (e: Exception) {
                uiThread {
                    view?.onFinish()
                    view?.error(e.message.toString())
                }
            }
        }
    }


    fun getLocation(lat: String, lng: String) {

        view.onLoading()
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

                view.onFinish()
            } catch (t: Throwable) {
                view.onError(t.printStackTrace().toString())
                view.onFinish()
            }
        }
    }
}
//    fun uploadFotoMakanan(
//        param: HashMap<String, RequestBody?>,
//        file: MultipartBody.Part?,
//        view: DonasiView
//    ){
//        view.onLoading()
//
//        doAsync {
//            job = runBlocking {
//                launch(Dispatchers.IO) {
//                    try {
//                        val data = service.
//
//                    } catch (e: Exception) {
//
//                    }
//                }
//            }
//        }
//    }

