package com.example.foodxdonatur.register

import android.annotation.SuppressLint
import android.content.Context
import com.example.foodxdonatur.network.APIFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception

class RegisterPresenter(val context: Context, var view: RegisterView) {

    private val service = APIFactory.makeRetrofitService()

    @SuppressLint("SimpleDateFormat")
    fun register(nama: String, email: String, password: String, noTelp: String, jenisKelamin: String, alamat: String){

        view?.onLoading()

        doAsync {
            try {
                runBlocking {
                    launch(Dispatchers.IO) {
                        val data = service.registDonatur(nama, email, password, noTelp, jenisKelamin, alamat)
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