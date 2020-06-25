package com.example.foodxdonatur.model


import com.google.gson.annotations.SerializedName

data class DonasiResponse(
    @SerializedName("donasi")
    var donasi: Donasi?,
    @SerializedName("makanandonasi")
    var makanandonasi: Makanandonasi?,
    @SerializedName("message")
    var message: String?
) {
    data class Donasi(
        @SerializedName("alamat_penjemputan")
        var alamatPenjemputan: String?,
        @SerializedName("created_at")
        var createdAt: String?,
        @SerializedName("donatur_id")
        var donaturId: String?,
        @SerializedName("foto")
        var foto: String?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("komunitas_id")
        var komunitasId: String?,
        @SerializedName("latitude")
        var latitude: String?,
        @SerializedName("longitude")
        var longitude: String?,
        @SerializedName("notes")
        var notes: String?,
        @SerializedName("status")
        var status: String?,
        @SerializedName("updated_at")
        var updatedAt: String?,
        @SerializedName("waktu_penjemputan")
        var waktuPenjemputan: String?
    )

    data class Makanandonasi(
        @SerializedName("bau")
        var bau: String?,
        @SerializedName("berubahrasa")
        var berubahrasa: String?,
        @SerializedName("berubahtekstur")
        var berubahtekstur: String?,
        @SerializedName("berwarna")
        var berwarna: String?,
        @SerializedName("created_at")
        var createdAt: String?,
        @SerializedName("donasi_id")
        var donasiId: Int?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("jamur")
        var jamur: String?,
        @SerializedName("jumlah")
        var jumlah: String?,
        @SerializedName("makanan_id")
        var makananId: String?,
        @SerializedName("tgl_kadaluwarsa")
        var tglKadaluwarsa: String?,
        @SerializedName("tgl_produksi")
        var tglProduksi: String?,
        @SerializedName("unit")
        var unit: String?,
        @SerializedName("updated_at")
        var updatedAt: String?
    )
}