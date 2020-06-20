package com.example.foodxdonatur.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.foodxdonatur.database.FoodXRoomDatabase
import com.example.foodxdonatur.database.UserDB
import com.example.foodxdonatur.database.UserDao
import com.example.foodxdonatur.database.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import kotlin.coroutines.CoroutineContext

class UserViewModel(application: Application): AndroidViewModel(application) {


    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val userRepository: UserRepository
    val allUser: LiveData<List<UserDB>>

    init {
        val userDao = FoodXRoomDatabase.getDatabase(application, scope).userDao()
        userRepository = UserRepository(userDao)
        allUser = userRepository.all
    }


    fun insertUser(user: UserDB) = scope.launch(Dispatchers.IO){
//        Log.e("INSERT DATA", )
        doAsync {
            userRepository.insert(user)
        }
    }


    fun deleteAll(){
        userRepository.deleteAll()
    }

    fun delete(user: UserDB){
        userRepository.deleteUser(user)
    }

}