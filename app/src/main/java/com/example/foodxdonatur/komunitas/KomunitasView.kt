package com.example.foodxdonatur.komunitas

import com.example.foodxdonatur.model.KomunitasResponse

interface KomunitasView {

    fun isLoading()
    fun stopLoading()
    fun showKomunitas(data: KomunitasResponse?)
}