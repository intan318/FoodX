package com.example.foodxdonatur.network

import com.example.foodxdonatur.model.*
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    ): Deferred<Response<LoginResponse>>


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

    @Multipart
    @Headers("Accept: application/json")
    @POST("createdonasi")
    fun createDonasi(
//       @Field("alamatPenjemputan") alamatPenjemputan: String?,
//       @Field("donatur_id") donaturId: String?,
//       @Field("foto") foto: String?,
//       @Field("komunitas_id") komunitasId: String?,
//       @Field("latitude") latitude: String?,
//       @Field("longitude") longitude: String?,
//       @Field("notes") notes: String?,
//       @Field("waktu_penjemputan") waktuPenjemputan: String?,
//       @Field("bau") bau: String?,
//       @Field("berubahrasa") berubahrasa: String?,
//       @Field("berubahtekstur") berubahtekstur: String?,
//       @Field("berwarna") berwarna: String?,
//       @Field("jamur") jamur: String?,
//       @Field("jumlah") jumlah: String?,
//       @Field("makanan_id") makananId: String?,
//       @Field("tgl_kadaluwarsa") tglKadaluwarsa: String?,
//       @Field("tgl_produksi") tglProduksi: String?,
//       @Field("unit") unit: String?
        @Header("Authorization") token: String,
        @PartMap data: HashMap<String, RequestBody?>,
        @Part foto: MultipartBody.Part?
    ): Deferred<Response<DonasiResponse>>

    @Headers("Accept: application/json")
    @GET("getmakanan")
    fun getMakanan(): Deferred<Response<MakananResponse>>

    @Headers("Accept: application/json")
    @GET("getuser")
    fun getUser(
        @Header("Authorization") token: String
    ): Deferred<Response<DonaturResponse>>


    @Headers("Accept: application/json")
    @GET("donasidonatur")
    fun getHistoryDonasi(
        @Header("Authorization") token: String,
        @Query("id") donasiId: String?,
        @Query("komunitas_id") komunitasId: String?,
        @Query("relawan_id") relawanId: String?,
        @Query("penerima_id") penerimaId: String?,
        @Query("alamat_penjemputan") alamatPenjemputan: String?,
        @Query("tgl_penjemputan") tglPenjemputan: String?,
        @Query("waktu_penjemputan") waktuPenjemputan: String?,
        @Query("latitude") latitude: String?,
        @Query("longitude") longitude: String?,
        @Query("notes") notes: String?,
        @Query("foto") foto: String?
    ): Deferred<Response<HistoryDonasiResponse>>

    //Google API//
    @Headers("Accept: application/json")
    @GET("geocode/json")
    fun getLocation(
        @Query("latlng") latLong : String,
        @Query("key") key : String
    ): Deferred<Response<LocationResponse>>

}