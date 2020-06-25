package com.example.foodxdonatur.komunitas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.foodxdonatur.R
import com.example.foodxdonatur.model.KomunitasResponse
import com.example.foodxdonatur.network.APIFactory
import com.example.foodxdonatur.utils.DialogView
import kotlinx.android.synthetic.main.activity_detail_komunitas.*

class DetailKomunitasActivity : AppCompatActivity() {

//    private lateinit var detail
    private lateinit var dialogView: DialogView
    private lateinit var komunitasPresenter: KomunitasPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_komunitas)
        val komunitas = intent.getSerializableExtra("komunitas") as KomunitasResponse.Komunitas

        txtNamaKomunitasDetail.text = komunitas.user?.name.toString()
        txtAlamatKomunitasDetail.text = komunitas.user?.alamat.toString()
        txtNoTelpKomunitasDetail.text = komunitas.user?.noTelp.toString()
        txtEmailKomunitasDetail.text = komunitas.user?.email.toString()

        val photo = APIFactory.BASE_URL_IMAGE+komunitas.fotoKomunitas!!

        Glide.with(this).load(photo).into(imgKomunitasDetail)
    }




}
