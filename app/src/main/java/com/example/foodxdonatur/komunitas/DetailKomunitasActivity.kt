package com.example.foodxdonatur.komunitas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.foodxdonatur.MainActivity
import com.example.foodxdonatur.R
import com.example.foodxdonatur.donasi.DonasiActivity
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

        txtNamaKomunitasDetail.text = komunitas.name.toString()
        txtAlamatKomunitasDetail.text = komunitas.alamat.toString()
        txtNoTelpKomunitasDetail.text = komunitas.noTelp.toString()
        txtEmailKomunitasDetail.text = komunitas.email.toString()

        val photo = APIFactory.BASE_URL_IMAGE+komunitas.fotoKomunitas!!

        Glide.with(this).load(photo).into(imgKomunitasDetail)

        buttonDonasi.setOnClickListener {
            val intent = Intent(applicationContext, DonasiActivity::class.java)
            intent.putExtra("komunitas", komunitas)
            startActivity(intent)
        }
    }




}
