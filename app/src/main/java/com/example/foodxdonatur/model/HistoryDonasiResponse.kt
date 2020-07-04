package com.example.foodxdonatur.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HistoryDonasiResponse(
    @SerializedName("donasi")
    var donasi: List<Donasi>?
): Serializable {
    data class Donasi(
        @SerializedName("accDonasi")
        var accDonasi: Boolean?,
        @SerializedName("alamat_penjemputan")
        var alamatPenjemputan: String?,
        @SerializedName("created_at")
        var createdAt: String?,
        @SerializedName("donatur_id")
        var donaturId: Int?,
        @SerializedName("foto")
        var foto: String?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("komunitas_id")
        var komunitasId: Int?,
        @SerializedName("latitude")
        var latitude: String?,
        @SerializedName("longitude")
        var longitude: String?,
        @SerializedName("notes")
        var notes: String?,
        @SerializedName("penerima_id")
        var penerimaId: Any?,
        @SerializedName("relawan_id")
        var relawanId: Any?,
        @SerializedName("status")
        var status: String?,
        @SerializedName("tgl_penjemputan")
        var tglPenjemputan: String?,
        @SerializedName("updated_at")
        var updatedAt: String?,
        @SerializedName("waktu_penjemputan")
        var waktuPenjemputan: String?
    ): Serializable
}