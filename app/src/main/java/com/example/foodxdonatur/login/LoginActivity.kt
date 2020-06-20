package com.example.foodxdonatur.login

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.example.foodxdonatur.MainActivity
import com.example.foodxdonatur.R
import com.example.foodxdonatur.database.UserDB
import com.example.foodxdonatur.model.UserResponse
import com.example.foodxdonatur.register.RegisterActivity
import com.example.foodxdonatur.utils.DialogView
import com.example.foodxdonatur.utils.SessionManager
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(),
    LoginView {

    private lateinit var loginPresenter: LoginPresenter
//    private lateinit var session: SessionManager

    private lateinit var dialogView: DialogView
    private lateinit var dialog: DialogInterface
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPresenter =
            LoginPresenter(this, this)
        dialogView = DialogView(this)
        
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        buttonSignIn.setOnClickListener {
            val email = editTextEmail.text?.trim().toString()
            val pass = editTextPassword.text?.trim().toString()

            if (validateLogin(email, pass)){
                doRequest()
            }

        }

        txtSignUp.setOnClickListener{
            val intentToRegister = Intent(this,RegisterActivity::class.java)
            startActivity(intentToRegister)
        }
    }

    private fun validateLogin(email: String, pass: String): Boolean{
        return when{
            email.trim().isEmpty() -> {
                editTextEmail.error = "Masukan e-mail Anda"
                editTextEmail.isFocusable = true
                editTextEmail.requestFocus()
                false
            }

            pass.trim().isEmpty() -> {
                editTextEmail.error = "Masukan password Anda"
                editTextEmail.requestFocus()
                false
            }

            else->true
        }

    }

    private fun doRequest(){
        Log.i("test", "dorequest")
        loginPresenter =
            LoginPresenter(this, this)
        loginPresenter.login(editTextEmail.text.toString(), editTextPassword.text.toString())
    }

    override fun onLoading() {
        dialogView.showProgressDialog()
    }

    override fun onFinish() {
        dialogView.hideProgressDialog()
    }

    override fun getResponses(loginResponse: UserResponse?) {

        if (loginResponse != null )
        {
            val user = UserDB(
                name = loginResponse.user?.name,
                email =  loginResponse.user?.email,
                alamat = loginResponse.user?.alamat,
                no_telp =  loginResponse.user?.noTelp
            )
            userViewModel.insertUser(user)
            loginResponse.user?.id?.let { goToHome(it) }

        }
    }


    private fun goToHome(id: Int) {
        SessionManager.getInstance(applicationContext).saveUser(id)
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

        override fun onStart() {
            super.onStart()

            if (SessionManager.getInstance(this).isLoggedIn) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }

}
