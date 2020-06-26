package com.example.foodxdonatur.donasi

import com.example.foodxdonatur.model.DonasiResponse
import com.example.foodxdonatur.model.MakananResponse
import com.example.foodxdonatur.model.RegisterResponse

interface DonasiView {

    fun onLoading()
    fun onFinish()
    fun getResponses(success : DonasiResponse?)
    fun getMakananResponse(success: MakananResponse?)
    fun error (error : String)

}