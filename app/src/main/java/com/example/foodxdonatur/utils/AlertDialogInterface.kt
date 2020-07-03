package com.example.foodxdonatur.utils

import android.content.DialogInterface

interface AlertDialogInterface {
    fun positiveButtonAction(dialog : DialogInterface, id : Int)
    fun negativeButtonAction(dialog: DialogInterface, id : Int)
}