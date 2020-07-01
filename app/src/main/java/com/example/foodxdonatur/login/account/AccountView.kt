package com.example.foodxdonatur.login.account

import com.example.foodxdonatur.login.UserViewModel
import com.example.foodxdonatur.model.DonaturResponse

interface AccountView {
    fun onLoading()
    fun onFinish()
    fun onError(error : String)
    fun getResponses(data : DonaturResponse?)
}