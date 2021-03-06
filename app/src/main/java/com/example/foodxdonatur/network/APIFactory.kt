package com.example.foodxdonatur.network

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIFactory {
//    const val BASE_URL: String = "http://foodx.skripsi-tik.xyz/api/"
    const val BASE_URL: String = "http://192.168.100.35:8000/api/"
//    const val BASE_URL_IMAGE: String = "http://foodx.skripsi-tik.xyz/images/"
    const val BASE_URL_IMAGE: String = "http://192.168.100.35:8000/images/"
    const val GOOGLE_MAP_URL = "https://maps.googleapis.com/maps/api/"

    fun makeRetrofitService(): APIServices {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(makeOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(APIServices::class.java)
    }

    fun makeGoogleService(): APIServices {
        return Retrofit.Builder()
            .baseUrl(GOOGLE_MAP_URL)
            .client(makeOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(APIServices::class.java)
    }

    private fun makeOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(makeLoggingInterceptor())
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level =
            HttpLoggingInterceptor.Level.BODY
        return logging
    }
}