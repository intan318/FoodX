package com.example.foodxdonatur.donasi

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.foodxdonatur.R
import com.example.foodxdonatur.login.UserViewModel
import com.example.foodxdonatur.utils.DialogView

class DonasiActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    private var chooseUnit: String? = null
    private val unit = arrayOf("porsi", "kg")



    private lateinit var dialogView: DialogView
    private lateinit var dialog: DialogInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donasi)


        val spinnerUnit = findViewById<Spinner>(R.id.spinnerUnitPorsi)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unit)
        spinnerUnit.adapter = adapter
        spinnerUnit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                chooseUnit = unit[position]
            }

        }
    }


}