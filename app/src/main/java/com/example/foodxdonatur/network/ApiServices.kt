package com.example.foodxdonatur.network

import com.example.foodxdonatur.model.RegisterResponse
import com.example.foodxdonatur.model.UserResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiServices {

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("regisdonatur")
    fun registDonatur(
        @Field("name") name: String?,
        @Field("email") email: String?,
        @Field("password") password: String?,
        @Field("no_telp") no_telp : String?,
        @Field("jenis_kelamin") jenis_kelamin : String?,
        @Field("alamat") alamat: String?
    ): Deferred<Response<RegisterResponse>>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Deferred<Response<UserResponse>>

//    @FormUrlEncoded
//    @Headers("Accept: application/json")
//    @POST("createdonasi")
//    fun createdonasi():
}