package com.example.foodxdonatur.database

import android.os.AsyncTask
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

    fun deleteAll(){
        DeleteAllUsersAsyncTask(userDao).execute()
    }

    private class DeleteAllUsersAsyncTask internal constructor(private val mAsyncTaskDao: UserDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }

    fun deleteUser(user: UserDB){
        DeleteUserAsyncTask(userDao).execute(user)
    }

    private class DeleteUserAsyncTask internal constructor(private val mAsyncTaskDao: UserDao) :
            AsyncTask<UserDB, Void, Void>(){

        override fun doInBackground(vararg params: UserDB?): Void? {
            params[0]?.let { mAsyncTaskDao.delete(it) }
            return null
        }
    }

}