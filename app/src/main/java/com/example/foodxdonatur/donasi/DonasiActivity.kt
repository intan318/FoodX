package com.example.foodxdonatur.donasi

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
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
import com.example.foodxdonatur.model.MakananResponse
import com.example.foodxdonatur.utils.DialogView
import com.example.foodxdonatur.utils.ImageController
import com.example.foodxdonatur.utils.ProgressRequestBody
import kotlinx.android.synthetic.main.activity_donasi.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*

class DonasiActivity : AppCompatActivity(), DonasiView, ProgressRequestBody.UploadCallBacks {

    private lateinit var userViewModel: UserViewModel
    private lateinit var donasiPresenter: DonasiPresenter
    private lateinit var dialogView: DialogView
    private lateinit var dialog: DialogInterface

    internal lateinit var imageController: ImageController

    private val unit = arrayOf("porsi", "kg")
    private var chooseUnit: String? = null

    private var listNamaMakanan: MutableList<MakananResponse.Makanan> = mutableListOf()

    val DATE_FORMAT_FROM_SERVER = "yyyy-MM-dd"
    val TARGET_DATE_FORMAT = "EEEE, dd MMM yyyy"

    val id = Locale("in", "ID")
    val simpleBasicDateFormat = SimpleDateFormat(DATE_FORMAT_FROM_SERVER, id)
    val newFormat = SimpleDateFormat(TARGET_DATE_FORMAT, id)

    private var dateProduksi = ""
    private var dateKadaluwarsa = ""
    private var datePenjemputan = ""
    private var timePenjemputan = ""
    var idMakanan = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donasi)

        dialogView = DialogView(this)

        imageController = ImageController(this, imgMakanan, this)

        donasiPresenter = DonasiPresenter(this, this, this)
        donasiPresenter.makanan()

//        val txtNamaMakanan = spinnerNamaMakanan.selectedItem.toString().trim()
        val txtJumlahMakanan = editTextPorsi.text.toString().trim()
//        val txtUnitJumlah = spinnerUnitPorsi.selectedItem.toString().trim()
        val txtDateProduksi = editTglProduksi.text.toString().trim()
        val txtDateKadaluwarsa = editTglKadaluwarsa.text.toString().trim()
        val txtDatePenjemputan = editHariJemput.text.toString().trim()
        val txtWaktuPenjemputan = editWaktuJemput.text.toString().trim()
        val txtNotes = editNotes.text.toString().trim()

        val txtAlamatPenjemputan = editLokasiJemput.text.toString().trim()


        val params = HashMap<String, RequestBody?>()

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)


        editTglProduksi.setOnClickListener {
            val dpTglProduksi = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    editTglProduksi.setText(newFormat.format(simpleBasicDateFormat.parse("$mYear-${mMonth + 1}-$mDay")!!))
                    dateProduksi = "$mYear-${mMonth + 1}-$mDay"
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

        editHariJemput.setOnClickListener {
            val dpTglJemput = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, pYear, pMonth, pDay ->
                    editHariJemput.setText(newFormat.format(simpleBasicDateFormat.parse("$pYear-${pMonth + 1}-$pDay")!!))
                    datePenjemputan = "pYear-${pMonth + 1}-$pDay"
                },
                year,
                month,
                day
            )

            dpTglJemput.show()
            dpTglJemput.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN)
            dpTglJemput.getButton(DatePickerDialog.BUTTON_NEUTRAL).setTextColor(Color.GREEN)
            dpTglJemput.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GREEN)
        }

        editWaktuJemput.setOnClickListener {
            val tpWaktuJemput = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    editWaktuJemput.setText("$hourOfDay:$minute")
                    timePenjemputan = "$hourOfDay:$minute"
                },
                hour,
                minute,
                true
            )

            tpWaktuJemput.show()
        }

        buttonChooseFile.setOnClickListener {
            imageController.requestImageCapturingPermissions()
        }

        buttonSubmit.setOnClickListener {

            if (cbTidakBerwarna.isChecked && cbTidakBertekstur.isChecked && cbTidakBerasa.isChecked
                && cbTidakBerbau.isChecked && cbTidakBerbau.isChecked && cbTidakBerjamur.isChecked
                && cbPernyataan.isChecked) {
                params["alamatPenjemputan"] = imageController.createPartFromString(txtAlamatPenjemputan)
//            params["komunitas_id"] = imageController.createPartFromString()
//            params["latitude"] = imageController.createPartFromString()
//            params["longitude"] = imageController.createPartFromString()
                params["notes"] = imageController.createPartFromString(txtNotes)
                params["tgl_penjemputan"] = imageController.createPartFromString(txtDatePenjemputan)
                params["waktu_penjemputan"] = imageController.createPartFromString(txtWaktuPenjemputan)
                params["bau"] = imageController.createPartFromString(false.toString())
                params["berubahrasa"] = imageController.createPartFromString(false.toString())
                params["berubahtekstur"] = imageController.createPartFromString(false.toString())
                params["berwarna"] = imageController.createPartFromString(false.toString())
                params["jamur"] = imageController.createPartFromString(false.toString())
                params["jumlah"] = imageController.createPartFromString(txtJumlahMakanan)
                params["makanan_id"] = imageController.createPartFromString(idMakanan)
                params["tgl_kadaluwarsa"] = imageController.createPartFromString(txtDateKadaluwarsa)
                params["tgl_produksi"] = imageController.createPartFromString(txtDateProduksi)
                params["unit"] = imageController.createPartFromString(chooseUnit)
                val part: MultipartBody.Part? = imageController.getMultiPartBody(imageController.realPath, "foto")

                donasiPresenter

            }

        }

    }

    override fun onLoading() {
        dialogView.showProgressDialog()
    }

    override fun onFinish() {
        dialogView.hideProgressDialog()
    }

    override fun getResponses(success: DonasiResponse?) {
        //handle setelah semua field diisi


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

    override fun onProgressUpdate(percentage: Int) {
        TODO("Not yet implemented")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageController.afterCapture(requestCode, resultCode, data)
    }


}