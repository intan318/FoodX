package com.example.foodxdonatur.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import org.jetbrains.anko.doAsync

class UserRepository(private val userDao: UserDao){

    val all: LiveData<List<UserDB>> = userDao.getAll()


    @WorkerThread
    fun insert(user: UserDB){
        doAsync {
            userDao.insert(user)
        }
    }

}