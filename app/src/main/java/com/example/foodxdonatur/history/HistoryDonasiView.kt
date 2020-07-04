package com.example.foodxdonatur.history

import com.example.foodxdonatur.model.HistoryDonasiResponse

interface HistoryDonasiView {

    fun isLoading()
    fun stopLoading()
    fun getResponses(data: HistoryDonasiResponse?)
}