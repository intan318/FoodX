package com.example.foodxdonatur

import android.app.Activity
import android.app.Dialog
import android.view.Window

class DialogView (private var activity: Activity){

    private lateinit var dialog: Dialog
    var status: Boolean = false

    fun showProgressDialog(){
        dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.loading_layout)

        dialog.show()
        status = true
    }

    fun hideProgressDialog() {
        dialog.dismiss()
        status = false
    }
}