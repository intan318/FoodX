package com.example.foodxdonatur.login

import android.content.Context
import com.example.foodxdonatur.login.LoginView
import com.example.foodxdonatur.network.ApiFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception

class LoginPresenter(val context: Context, val view: LoginView) {
//    private var isRegistered = false
//    private var isPassword = true
//    private var isLogged = false
//    private var message = " "
//    private var id = ""

    private val service = ApiFactory.makeRetrofitService()

    fun login(email: String, password: String) {

        view.onLoading()

        doAsync {
            runBlocking {
                launch(Dispatchers.IO) {
                    try {
                        val login = service.login(email, password)
                        val result = login.await()

                        uiThread {
                            view.getResponses(result.body())
                            view.onFinish()
                        }

                    } catch (e: Exception) {
                        uiThread {
                            view.onFinish()
                        }

                    }
                }
            }

        }
    }

}