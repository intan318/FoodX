package com.example.foodxdonatur.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class UserDB(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "email")
    var email: String? = null,
    @ColumnInfo(name = "alamat")
    var alamat: String? = null,
    @ColumnInfo(name = "no_telp")
    var no_telp: String? = null,
    @ColumnInfo(name = "jenis_kelamin")
    var jenis_kelamin: String? = null
)