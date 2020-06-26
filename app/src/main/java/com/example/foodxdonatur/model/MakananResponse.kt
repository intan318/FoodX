package com.example.foodxdonatur.model


import com.google.gson.annotations.SerializedName

data class MakananResponse(
    @SerializedName("makanan")
    var makanan: List<Makanan>?
) {
    data class Makanan(
        @SerializedName("created_at")
        var createdAt: Any?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("jenis_makanan_id")
        var jenisMakananId: Int?,
        @SerializedName("nama_makanan")
        var namaMakanan: String?,
        @SerializedName("updated_at")
        var updatedAt: Any?
    )
}