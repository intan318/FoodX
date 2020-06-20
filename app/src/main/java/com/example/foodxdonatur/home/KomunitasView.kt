package com.example.foodxdonatur.home

import com.example.foodxdonatur.model.KomunitasResponse

interface KomunitasView {

    fun isLoading()
    fun stopLoading()
    fun showKomunitas(data: KomunitasResponse?)
}