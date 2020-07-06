package com.example.foodxdonatur.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.foodxdonatur.R
import com.example.foodxdonatur.model.HistoryDonasiResponse
import com.example.foodxdonatur.network.APIFactory
import kotlinx.android.synthetic.main.activity_detail_history_donasi.*
import kotlinx.android.synthetic.main.activity_detail_komunitas.*
import kotlinx.android.synthetic.main.card_history_donasi.*

class DetailHistoryDonasi : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_history_donasi)

        val donasi = intent.getSerializableExtra("donasi") as HistoryDonasiResponse.Donasi

        txtTglDonasiDetail.text = donasi.tglPenjemputan.toString()
        txtAlamatDonasiDetail.text = donasi.alamatPenjemputan.toString()
        txtStatusDonasi.text = donasi.status.toString()

        val photo = APIFactory.BASE_URL_IMAGE+donasi.foto!!

        Glide.with(this).load(photo).into(imgDonasiDetail)
    }
}