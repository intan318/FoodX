package com.example.foodxdonatur.model


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("message")
    var message: String?
)