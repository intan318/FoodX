package com.example.foodxdonatur.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodxdonatur.R
import com.example.foodxdonatur.model.HistoryDonasiResponse

class DetailHistoryDonasi : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_history_donasi)

        val donasi = intent.getSerializableExtra("donasi") as HistoryDonasiResponse
    }
}