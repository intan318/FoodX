package com.example.foodxdonatur.network

import com.example.foodxdonatur.model.KomunitasResponse
import com.example.foodxdonatur.model.RegisterResponse
import com.example.foodxdonatur.model.UserResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface APIServices {

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("regisdonatur")
    fun registDonatur(
        @Field("name") name: String?,
        @Field("email") email: String?,
        @Field("password") password: String?,
        @Field("no_telp") noTelp : String?,
        @Field("jenis_kelamin") jenisKelamin : String?,
        @Field("alamat") alamat: String?
    ): Deferred<Response<RegisterResponse>>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Deferred<Response<UserResponse>>



    @Headers("Accept: application/json")
    @GET("komunitas")
    fun getKomunitas(
        @Header("Authorization") token: String,
        @Query("name") name: String?,
        @Query("email") email: String?,
        @Query("no_telp") noTelp: String?,
        @Query("alamat") alamat: String?,
        @Query("foto_komunitas") fotoKomunitas: String?
    ): Deferred<Response<KomunitasResponse>>


}