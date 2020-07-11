package com.example.foodxdonatur.model


import com.google.gson.annotations.SerializedName

data class PDResponse(
    @SerializedName("penerimadonasi")
    var penerimadonasi: List<Penerimadonasi?>?
) {
    data class Penerimadonasi(
        @SerializedName("alamat_penerima")
        var alamatPenerima: String?,
        @SerializedName("jarak")
        var jarak: String?,
        @SerializedName("latitude")
        var latitude: String?,
        @SerializedName("longitude")
        var longitude: String?,
        @SerializedName("nama_penerima")
        var namaPenerima: String?
    )
}