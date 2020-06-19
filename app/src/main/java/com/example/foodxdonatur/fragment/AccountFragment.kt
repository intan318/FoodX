package com.example.foodxdonatur.fragment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.example.foodxdonatur.MainActivity
import com.example.foodxdonatur.R
import com.example.foodxdonatur.login.UserViewModel
import com.example.foodxdonatur.utils.DialogView
import com.example.foodxdonatur.utils.SessionManager
import kotlinx.android.synthetic.main.fragment_account.*
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.okButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.toast


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AccountFragment : Fragment() {
    private lateinit var dialogView: DialogView
    private lateinit var dialog: DialogInterface
    private lateinit var dialogAlertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userViewModel = ViewModelProviders.of(this@AccountFragment).get(UserViewModel::class.java)

        buttonSignOut.setOnClickListener {
              dialog = alert("Apakah anda yakin ingin keluar dari akun ini?",
              "Log out"){
                  okButton {
                      SessionManager.getInstance(this@AccountFragment.context!!).clear()
                      val intent = Intent(activity, MainActivity::class.java)
                      intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                      userViewModel.deleteAll()
                      startActivity(intent)
                      activity?.finish()
                      toast("Anda sudah keluar akun")
                  }
                  cancelButton {
                      dialogAlertDialog.dismiss()
                  }
              }.show()

        }

//        init()

    }
//
//    private fun init() {
//
//        if(!SessionManager.getInstance(activity!!).isLoggedIn){
//
//        }
//    }

}
