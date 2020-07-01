package com.example.foodxdonatur.login.account

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.foodxdonatur.R
import com.example.foodxdonatur.database.UserDB
import com.example.foodxdonatur.komunitas.KomunitasPresenter
import com.example.foodxdonatur.login.LoginActivity
import com.example.foodxdonatur.login.UserViewModel
import com.example.foodxdonatur.model.DonaturResponse
import com.example.foodxdonatur.utils.DialogView
import com.example.foodxdonatur.utils.SessionManager
import kotlinx.android.synthetic.main.fragment_account.*
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.okButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.support.v4.toast
import java.lang.IllegalArgumentException
import java.lang.IndexOutOfBoundsException
import java.lang.NullPointerException


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AccountFragment : Fragment(), AccountView {
    private lateinit var dialogView: DialogView
    private lateinit var dialog: DialogInterface
    private lateinit var accountPresenter: AccountPresenter
    private lateinit var userViewModel: UserViewModel

    private var lastId : Int? = null

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

        userViewModel = ViewModelProviders.of(this@AccountFragment).get(UserViewModel::class.java)
        dialogView = DialogView(context!! as Activity)


        accountPresenter = AccountPresenter(context!!, this)
        accountPresenter.getUser(token = SessionManager.getInstance(context!!).getToken()!!)

        initialUser()

        buttonSignOut.setOnClickListener {
              dialog = alert("Apakah anda yakin ingin keluar dari akun ini?",
              "Log out"){
                  okButton {
                      SessionManager.getInstance(this@AccountFragment.context!!).clear()
                      val intent = Intent(activity, LoginActivity::class.java)
                      intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                      userViewModel.deleteAll()
                      startActivity(intent)
                      activity?.finish()
                      toast("Anda sudah keluar akun")
                  }
                  cancelButton {
                      dialog.dismiss()
                  }
              }.show()

        }

    }

    private fun initialUser(){
        var encounter = 1
        userViewModel.allUser.observe(this, Observer {
            try {
                if(it.isNotEmpty()) {
                    Log.e("USER", it[0].toString())
                    attachData(it[0])
                    lastId = it[0].id
                    if(encounter == 1) {
                        accountPresenter.getUser(
                            SessionManager.getInstance(context!!).getToken().toString())
                        encounter++
                    }
                }
            }catch (e : IndexOutOfBoundsException){

            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun attachData(user: UserDB){

        txtNamaLengkap.text = user.name
        txtEmailUser.text = user.email
        txtNoHp.text = user.no_telp
        txtAlamat.text = user.alamat
    }

    override fun onLoading() {
       // dialogView.showProgressDialog()
    }

    override fun onFinish() {
        runOnUiThread {
           // dialogView.hideProgressDialog()
        }
    }

    override fun onError(error: String) {

    }

    override fun getResponses(data: DonaturResponse?) {
        try {
            if (data != null) {
                val user = data.user?.convertToTable()
                user?.id = lastId
                if (user != null) {
                    attachData(user)
                }

            }
        } catch (e: Exception) {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == 1 && resultCode == Activity.RESULT_OK){
            initialUser()
        }
    }
}
