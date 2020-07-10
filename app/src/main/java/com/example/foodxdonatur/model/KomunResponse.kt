package com.example.foodxdonatur.model


import com.google.gson.annotations.SerializedName

data class KomunResponse(
    @SerializedName("komunitas")
    var komunitas: List<Komunitas?>?
) {
    data class Komunitas(
        @SerializedName("alamat")
        var alamat: String?,
        @SerializedName("created_at")
        var createdAt: String?,
        @SerializedName("email")
        var email: String?,
        @SerializedName("email_verified_at")
        var emailVerifiedAt: Any?,
        @SerializedName("foto_komunitas")
        var fotoKomunitas: String?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("legalitas")
        var legalitas: String?,
        @SerializedName("name")
        var name: String?,
        @SerializedName("no_telp")
        var noTelp: String?,
        @SerializedName("password")
        var password: String?,
        @SerializedName("remember_token")
        var rememberToken: Any?,
        @SerializedName("status")
        var status: Boolean?,
        @SerializedName("tgl_berdiri")
        var tglBerdiri: String?,
        @SerializedName("updated_at")
        var updatedAt: String?,
        @SerializedName("user_id")
        var userId: Int?
    )
}