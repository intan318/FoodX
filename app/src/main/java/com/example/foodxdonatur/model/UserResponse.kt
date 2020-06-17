package com.example.foodxdonatur.model


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("access_token")
    var accessToken: String?,
    @SerializedName("user")
    var user: User?
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