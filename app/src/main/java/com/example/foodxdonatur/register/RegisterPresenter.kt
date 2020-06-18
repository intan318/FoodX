package com.example.foodxdonatur.register

import android.annotation.SuppressLint
import android.content.Context
import com.example.foodxdonatur.network.ApiFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception

class RegisterPresenter(val context: Context, var view: RegisterView) {

    private val service = ApiFactory.makeRetrofitService()

    @SuppressLint("SimpleDateFormat")
    fun register(nama: String, email: String, password: String, no_telp: String, jenis_kelamin: String, alamat: String){

        view?.onLoading()

        doAsync {
            try {
                runBlocking {
                    launch(Dispatchers.IO) {
                        val data = service.registDonatur(nama, email, password, no_telp, jenis_kelamin, alamat)
                        val result = data.await()

                        uiThread {
//                            view?.getResponses(result.body()?.error!!)
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

}