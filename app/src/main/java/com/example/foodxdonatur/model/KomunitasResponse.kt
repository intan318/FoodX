package com.example.foodxdonatur.model


import com.google.gson.annotations.SerializedName

data class KomunitasResponse(
    @SerializedName("komunitas")
    var komunitas: List<Komunitas?>?
) {
    data class Komunitas(
        @SerializedName("created_at")
        var createdAt: String?,
        @SerializedName("foto_komunitas")
        var fotoKomunitas: String?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("legalitas")
        var legalitas: String?,
        @SerializedName("status")
        var status: Boolean?,
        @SerializedName("tgl_berdiri")
        var tglBerdiri: String?,
        @SerializedName("updated_at")
        var updatedAt: String?,
        @SerializedName("user")
        var user: User?,
        @SerializedName("user_id")
        var userId: Int?
    ) {
        data class User(
            @SerializedName("alamat")
            var alamat: String?,
            @SerializedName("created_at")
            var createdAt: String?,
            @SerializedName("email")
            var email: String?,
            @SerializedName("email_verified_at")
            var emailVerifiedAt: Any?,
            @SerializedName("id")
            var id: Int?,
            @SerializedName("name")
            var name: String?,
            @SerializedName("no_telp")
            var noTelp: String?,
            @SerializedName("updated_at")
            var updatedAt: String?
        )
    }
}