package com.example.foodxdonatur.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * from users")
    fun getAll(): LiveData<List<UserDB>>

    @Insert()
    fun insert(userDB: UserDB)
}