package com.example.foodxdonatur.donasi

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.foodxdonatur.R
import com.example.foodxdonatur.login.UserViewModel
import com.example.foodxdonatur.model.DonasiResponse
import com.example.foodxdonatur.model.KomunitasResponse
import com.example.foodxdonatur.model.MakananResponse
import com.example.foodxdonatur.register.RegisterPresenter
import com.example.foodxdonatur.utils.DialogView
import kotlinx.android.synthetic.main.activity_donasi.*
import java.text.SimpleDateFormat
import java.util.*

class DonasiActivity : AppCompatActivity(), DonasiView {

    private lateinit var userViewModel: UserViewModel
    private lateinit var donasiPresenter: DonasiPresenter
    private lateinit var dialogView: DialogView
    private lateinit var dialog: DialogInterface

    private val unit = arrayOf("porsi", "kg")
    private var chooseUnit: String? = null

    private var listNamaMakanan: MutableList<MakananResponse.Makanan> = mutableListOf()

    val DATE_FORMAT_FROM_SERVER = "yyyy-MM-dd"
    val TARGET_DATE_FORMAT = "EEEE, dd MMM yyyy"

    val id = Locale("in", "ID")
    val simpleBasicDateFormat = SimpleDateFormat(DATE_FORMAT_FROM_SERVER, id)
    val newFormat = SimpleDateFormat(TARGET_DATE_FORMAT, id)

    private var realDate = ""
    private var dateKadaluwarsa = ""
    var idMakanan = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donasi)

        dialogView = DialogView(this)
        donasiPresenter = DonasiPresenter(this, this)
        donasiPresenter.makanan()
        handleDonasi()


        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)

        editTglProduksi.setOnClickListener {
            val dpTglProduksi = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    editTglProduksi.setText(newFormat.format(simpleBasicDateFormat.parse("$mYear-${mMonth + 1}-$mDay")!!))
                    realDate = "$mYear-${mMonth + 1}-$mDay"
                },
                year,
                month,
                day
            )

            dpTglProduksi.show()
            dpTglProduksi.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN)
            dpTglProduksi.getButton(DatePickerDialog.BUTTON_NEUTRAL).setTextColor(Color.GREEN)
            dpTglProduksi.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GREEN)
        }

        editTglKadaluwarsa.setOnClickListener {
            val dpTglKadaluwarsa = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, nYear, nMonth, nDay ->
                    editTglKadaluwarsa.setText(newFormat.format(simpleBasicDateFormat.parse("$nYear-${nMonth + 1}-$nDay")!!))
                    dateKadaluwarsa = "$nYear-${nMonth + 1}-$nDay"
                },
                year,
                month,
                day
            )

            dpTglKadaluwarsa.show()
            dpTglKadaluwarsa.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN)
            dpTglKadaluwarsa.getButton(DatePickerDialog.BUTTON_NEUTRAL).setTextColor(Color.GREEN)
            dpTglKadaluwarsa.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GREEN)
        }
    }

    private fun handleDonasi() {

        buttonSubmit.setOnClickListener {

        }

    }


    override fun onLoading() {
        dialogView.showProgressDialog()
    }

    override fun onFinish() {
        dialogView.hideProgressDialog()
    }

    override fun getResponses(success: DonasiResponse?) {
        //handle setelag semua field diisi/mencet button submit

    }

    override fun getMakananResponse(success: MakananResponse?) {

        success?.makanan?.let { listNamaMakanan.addAll(it) }
        val spinnerNamaMakanan = findViewById<Spinner>(R.id.spinnerNamaMakanan)
        val adapterMakanan = ArrayAdapter(this@DonasiActivity,
            android.R.layout.simple_spinner_dropdown_item,
            listNamaMakanan.map { it.namaMakanan }
        )

        spinnerNamaMakanan.adapter = adapterMakanan
        spinnerNamaMakanan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                idMakanan = listNamaMakanan[position].id.toString()
            }

        }

        val spinnerUnit = findViewById<Spinner>(R.id.spinnerUnitPorsi)
        val adapterUnit = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unit)
        spinnerUnit.adapter = adapterUnit
        spinnerUnit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

    override fun error(error: String) {
        TODO("Not yet implemented")
    }


}