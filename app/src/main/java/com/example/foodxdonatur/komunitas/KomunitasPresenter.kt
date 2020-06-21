package com.example.foodxdonatur.komunitas

import android.content.Context
import com.example.foodxdonatur.network.APIFactory
import com.example.foodxdonatur.network.APIServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception

class KomunitasPresenter(val context: Context, val view: KomunitasView) {

    private var service: APIServices = APIFactory.makeRetrofitService()
    var job: Job? = null

    fun getKomunitas(
        name: String? = null,
        email: String? = null,
        noTelp: String? = null,
        alamat: String? = null,
        fotoKomunitas: String? = null
    ){
        view.isLoading()

        doAsync {
            GlobalScope.launch(Dispatchers.Main){
                try {
                  val data = service.getKomunitas(
                      name = name,
                      email = email,
                      noTelp = noTelp,
                      alamat = alamat,
                      fotoKomunitas = fotoKomunitas
                  )
                  val result = data.await()
                    uiThread {
                        view.showKomunitas(result.body())
                        view.stopLoading()
                    }
                } catch (e: Exception){
                    uiThread {
                        view.stopLoading()
                    }

                }
            }
        }

    }
}