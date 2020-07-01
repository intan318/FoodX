package com.example.foodxdonatur.login

import com.example.foodxdonatur.model.LoginResponse

interface LoginView {

    fun onLoading()
    fun onFinish()
    fun getResponses(loginResponse: LoginResponse?)
}