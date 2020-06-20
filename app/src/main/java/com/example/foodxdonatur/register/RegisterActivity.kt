package com.example.foodxdonatur.register

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.foodxdonatur.login.LoginActivity
import com.example.foodxdonatur.R
import com.example.foodxdonatur.model.RegisterResponse
import com.example.foodxdonatur.utils.DialogView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.okButton

class RegisterActivity : AppCompatActivity(),
    RegisterView {

    private lateinit var registerPresenter: RegisterPresenter
    private lateinit var dialog: DialogInterface
    private lateinit var dialogView: DialogView
    private var sukses = false
    private val gender = arrayOf("Laki-laki", "Perempuan")
    private var chooseGender: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dialogView = DialogView(this)
        registerPresenter =
            RegisterPresenter(this, this)
        handleRegister()

        val dropdown = findViewById<Spinner>(R.id.spinnerJenKelRegister)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, gender)
        dropdown.adapter = adapter
        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                chooseGender = gender[position]
            }

        }
    }

    private fun handleRegister(){
        buttonRegister.setOnClickListener {
            val nama = inputEditFullName.text.toString().trim()
            val email = inputEditEmail.text.toString().trim()
            val password = inputEditPassword.text.toString().trim()
            val noTelp = inputEditPhoneNum.text.toString().trim()
            val jenisKelamin = chooseGender.toString()
            val alamat = inputEditAddress.text.toString().trim()

            if (nama.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() &&
                noTelp.isNotEmpty() && jenisKelamin.isNotEmpty() && alamat.isNotEmpty()){
                registerPresenter.register(nama, email, password, noTelp, jenisKelamin, alamat)
            } else {
                "Harap semua data di isi!"
            }
        }
    }

    override fun onLoading() {

        dialogView.showProgressDialog()
    }

    override fun onFinish() {

        dialogView.hideProgressDialog()
    }

    override fun getResponses(success: RegisterResponse?) {
        dialog =
            alert(
                message = "Registrasi berhasil! Silakan login ",
                title = "Berhasil"
            ) {
                okButton {
                    startActivity(intentFor<LoginActivity>("pesan" to "Silakan login"))
                    finish()
                }

                setFinishOnTouchOutside(false)
            }.show()
    }

    override fun error(error: String) {

    }
}


