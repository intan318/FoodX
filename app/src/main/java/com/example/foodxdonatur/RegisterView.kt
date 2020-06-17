package com.example.foodxdonatur

import com.example.foodxdonatur.model.RegisterResponse

interface RegisterView {

    fun onLoading()
    fun onFinish()
    fun getResponses(success : RegisterResponse?)
    fun error (error : String)
}