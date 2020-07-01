package com.example.foodxdonatur.model


import com.example.foodxdonatur.database.UserDB
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DonaturResponse(
    @SerializedName("user")
    var user: User?
) : Serializable {
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
        @SerializedName("password")
        var password: String?,
        @SerializedName("remember_token")
        var rememberToken: Any?,
        @SerializedName("role_id")
        var roleId: Int?,
        @SerializedName("updated_at")
        var updatedAt: String?,
        @SerializedName("user_id")
        var userId: Int?
    ) : Serializable {
        fun convertToTable(): UserDB{
            return UserDB(
                id = id,
                name =  name,
                email = email,
                alamat = alamat,
                no_telp = noTelp
            )
        }
    }
}