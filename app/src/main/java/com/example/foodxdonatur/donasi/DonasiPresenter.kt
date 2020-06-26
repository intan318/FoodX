package com.example.foodxdonatur.donasi

import android.annotation.SuppressLint
import android.content.Context
import com.example.foodxdonatur.network.APIFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception

class DonasiPresenter(val context: Context, var view:DonasiView) {

    private val service = APIFactory.makeRetrofitService()

    @SuppressLint("SimpleDateFormat")
    fun donasi(alamatPenjemputan: String, donaturId: String, foto: String, komunitasId: String,
               latitude: String, longitude: String, notes: String, waktuPenjemputan: String,
               bau: String, berubahrasa: String, berubahtekstur: String, berwarna: String,
               jamur: String, jumlah: String, makananId: String, tglKadaluwarsa: String,
               tglProduksi: String, unit: String) {

        view?.onLoading()

        doAsync {
            try {
                runBlocking {
                    launch(Dispatchers.IO) {
                        val data = service.createDonasi(alamatPenjemputan, donaturId, foto, komunitasId,
                            latitude, longitude, notes, waktuPenjemputan, bau, berubahrasa, berubahtekstur,
                            berwarna, jamur, jumlah, makananId, tglKadaluwarsa, tglProduksi, unit)
                        val result = data.await()

                        uiThread {
                            view?.getResponses(result.body())
                            view?.onFinish()
                        }
                    }
                }
            }
            catch (e: Exception){
                uiThread {
                    view?.onFinish()
                    view?.error(e.message.toString())
                }
            }
        }
    }

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

}