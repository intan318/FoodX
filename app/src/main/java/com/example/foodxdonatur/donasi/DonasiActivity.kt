package com.example.foodxdonatur.donasi

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.foodxdonatur.ChooseLocationActivity
import com.example.foodxdonatur.MainActivity
import com.example.foodxdonatur.R
import com.example.foodxdonatur.komunitas.KomunitasFragment
import com.example.foodxdonatur.login.LoginActivity
import com.example.foodxdonatur.login.UserViewModel
import com.example.foodxdonatur.model.*
import com.example.foodxdonatur.utils.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_donasi.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.okButton
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class DonasiActivity : AppCompatActivity(), DonasiView, ProgressRequestBody.UploadCallBacks {

    private lateinit var userViewModel: UserViewModel
    private lateinit var donasiPresenter: DonasiPresenter
    private lateinit var dialogView: DialogView
    private lateinit var dialog: DialogInterface

    lateinit var coordinateGPS: Coordinate

    internal lateinit var imageController: ImageController

    private val unit = arrayOf("porsi", "kg")
    private var chooseUnit: String? = null

    private var listNamaMakanan: MutableList<MakananResponse.Makanan> = mutableListOf()
    private val mapKey = "MapViewFoodX"
    private lateinit var latLong: LatLng
    private var locationTitle: String? = null
    private var token: String? = null

    val GPS_CODE_REQUEST = 200
    val CODE_CHOOSE_LOCATION = 2001
    val DATE_FORMAT_FROM_SERVER = "yyyy-MM-dd"
    val TARGET_DATE_FORMAT = "EEEE, dd MMM yyyy"

    val id = Locale("in", "ID")
    val simpleBasicDateFormat = SimpleDateFormat(DATE_FORMAT_FROM_SERVER, id)
    val newFormat = SimpleDateFormat(TARGET_DATE_FORMAT, id)
    
    private var myMap: GoogleMap? = null

    private var sdf: SimpleDateFormat? = null

    private var dateProduksi = ""
    private var dateKadaluwarsa = ""
    private var datePenjemputan = ""
    private var timePenjemputan = ""

    var idMakanan = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donasi)
        captureCoordinateGPS()

        dialogView = DialogView(this)

        imageController = ImageController(this, imgMakanan, this)

        donasiPresenter = DonasiPresenter(this, this,  this)
        donasiPresenter.makanan()

        val komunitas = intent.getSerializableExtra("komunitas") as KomunitasResponse.Komunitas
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
                    Log.e("datekdlw", dateKadaluwarsa.toString())
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

                    Log.e("datejemput", datePenjemputan.toString())
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

        editLokasiJemput.setOnClickListener {
            startActivityForResult(
                Intent(this, ChooseLocationActivity::class.java),
                CODE_CHOOSE_LOCATION
            )
        }

        buttonChooseFile.setOnClickListener {
            imageController.requestImageCapturingPermissions()
        }

        buttonSubmit.setOnClickListener {
            toast("click")
            if (cbTidakBerwarna.isChecked && cbTidakBertekstur.isChecked && cbTidakBerasa.isChecked
                && cbTidakBerbau.isChecked && cbTidakBerbau.isChecked && cbTidakBerjamur.isChecked
                && cbPernyataan.isChecked) {


                toast("click kirim")

                params["alamat_penjemputan"] = imageController.createPartFromString(locationTitle)
                params["komunitas_id"] = imageController.createPartFromString(komunitas.id.toString())
                params["latitude"] = imageController.createPartFromString(latLong.latitude.toString())
                params["longitude"] = imageController.createPartFromString(latLong.longitude.toString())
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

                token = SessionManager.getInstance(this).getToken()
                donasiPresenter.insertDonasi(token, params, part)

            }else{
                toast("Kurang")
            }

        }

    }

    private fun makeLongToast(text: String) {
        runOnUiThread {
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }
    }

    private fun captureCoordinateGPS() {
        val gpsTracker by lazy { GPSChecker(this, this) }

        coordinateGPS = gpsTracker.check()
        Log.e("lat,lng orginal", coordinateGPS.lat.toString() + "," + coordinateGPS.lng.toString())
    }


    private fun  afterPermissionGPSRequest(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == GPS_CODE_REQUEST) {
            when {
                grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    val gps = GPSTracker(this, this)
                    gps.getLocation()
                    when {
                        gps.canGetLocation() -> {
                            val lat = gps.getLatitude()
                            val lng = gps.getLongitude()
                            coordinateGPS =
                                Coordinate(lat, lng)
                            //longToast("coordinate  is - \nLat: $lat\nLong: $lng")
                        }
                        else -> gps.showSettingsAlert()
                    }
                }
                else -> {
                    makeLongToast("You need to grant permission")
                }
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
//        if success

        runOnUiThread {
            dialog =
                alert(
                    message = "Donasi berhasil! ",
                    title = "Berhasil"
                ) {
                    okButton {
                        startActivity(intentFor<MainActivity>())
                        finish()
                    }

                    setFinishOnTouchOutside(false)
                }.show()
        }
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

    override fun onLocationResult(result: LocationResponse?, originalLatLng: LatLng) {
        TODO("Not yet implemented")
    }

    override fun error(error: String) {

    }

    override fun onProgressUpdate(percentage: Int) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageController.afterCapture(requestCode, resultCode, data)

        if (requestCode == CODE_CHOOSE_LOCATION && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                try {
                    val lat = data.getStringExtra("lat")
                    val lng = data.getStringExtra("lng")
                    val name = data.getStringExtra("name")!!
                    latLong = LatLng(lat!!.toDouble(), lng!!.toDouble())
                    locationTitle = name
                    editLokasiJemput.setText(name)

//                    updateDataDashboard(lat, lng)
                } catch (e: NullPointerException) {
                    Toast.makeText(this, "Error filter", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GPS_CODE_REQUEST) {

        }
    }

}