package com.example.foodxdonatur.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.foodxdonatur.database.UserDB

class SessionManager (private var context: Context) {

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences =
                context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getInt("id", -1) != -1
        }

    val user: SharedPreferences
        get() {
            val sharedPreferences =
                context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            sharedPreferences.getInt("id", 1)
            return sharedPreferences
//           return User(
//                sharedPreferences.getInt("id", 1),
//                sharedPreferences.getString("token", null),
//                sharedPreferences.getInt("status_code", 200),
//                sharedPreferences.getString("error", null)
//            )
        }

    fun getIdUser(): Int {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt("id", 1)
        return id
    }

    fun saveUser(user: Int?) {

        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        user?.let { editor.putInt("id", it) }
        editor.commit()

    }

    fun clear() {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {

        private val SHARED_PREF_NAME = "MY_SHARED_PREFERENCE"

        private var mInstance: SessionManager? = null

        @Synchronized
        fun getInstance(mCtx: Context): SessionManager {
            if (mInstance == null) {
                mInstance = SessionManager(mCtx)
            }
            return mInstance as SessionManager
        }

    }

}