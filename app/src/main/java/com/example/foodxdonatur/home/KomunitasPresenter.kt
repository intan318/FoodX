package com.example.foodxdonatur.home

import android.content.Context
import android.util.Log
import com.example.foodxdonatur.network.APIFactory
import com.example.foodxdonatur.network.APIServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class KomunitasPresenter(val context: Context, val view: KomunitasView) {
//
//    private var service: APIServices = APIFactory.makeRetrofitService(context)
//    var job: Job? = null
//
//    fun getKomunitas(page: Int){
//        view.isLoading()
//        GlobalScope.launch(Dispatchers.Main) {
//            try {
//                val data = service.getKomunitas(page)
//                val result = data.await()
//
//                when {
//                    result.code() == 401 || result.body()?.message == "Unauthorized" -> {
//                        view.onUnAuthorized()
//                    }
//                    result.code() == 204 -> {
//                        view.onEmptyData()
//                    }
//                    else -> {
//                        view.onResults(result.body())
//                    }
//                }
//                view.stopLoading()
//            } catch (e: Exception) {
//                Log.e("Error fetching komunitas", e.message.toString())
//                view.stopLoading()
//            }
//        }
//    }
}