package com.example.foodxdonatur.login

import com.example.foodxdonatur.model.UserResponse

interface LoginView {

    fun onLoading()
    fun onFinish()
    fun getResponses(loginResponse: UserResponse?)
}