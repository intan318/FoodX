package com.example.foodxdonatur.login.account

import android.content.Context
import androidx.room.Room
import com.example.foodxdonatur.database.UserRepository
import com.example.foodxdonatur.network.APIFactory
import com.example.foodxdonatur.network.APIServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.runOnUiThread
import java.lang.Exception

class AccountPresenter(val context: Context, val view: AccountView){

    private var service: APIServices = APIFactory.makeRetrofitService()
    var job: Job? = null

    fun getUser(token: String) {
        view.onLoading()

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val data = service.getUser("Bearer $token")
                val result = data.await()

                if (result.code() == 401 || result.body() == null) {
                    view.onFinish()
                } else {
                    context.runOnUiThread {
                        view.getResponses(result.body())
                        view.onFinish()
                    }
                }

            } catch (e: Exception){
                view.onError(e.toString())
                view.onFinish()
            } catch (e: NullPointerException) {
                view.onError(e.toString())
                view.onFinish()
            }
        }
    }


}