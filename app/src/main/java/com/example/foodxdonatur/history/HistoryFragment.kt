package com.example.foodxdonatur.history

import android.app.Activity
import android.os.Bundle
import android.se.omapi.Session
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodxdonatur.R
import com.example.foodxdonatur.komunitas.DetailKomunitasActivity
import com.example.foodxdonatur.komunitas.KomunitasAdapter
import com.example.foodxdonatur.model.HistoryDonasiResponse
import com.example.foodxdonatur.utils.DialogView
import com.example.foodxdonatur.utils.SessionManager
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_komunitas.*
import org.jetbrains.anko.support.v4.intentFor

class HistoryFragment : Fragment(), HistoryDonasiView {

    private lateinit var dialogView: DialogView
    private lateinit var historyDonasiAdapter: HistoryDonasiAdapter
    private lateinit var historyDonasiPresenter: HistoryDonasiPresenter

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
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogView = DialogView(context!! as Activity)
        historyDonasiPresenter = HistoryDonasiPresenter(context!!, this)
        historyDonasiPresenter.getHistoryDonasi(token = SessionManager.getInstance(context!!).getToken()!!)

    }
    override fun isLoading() {
        dialogView.showProgressDialog()
    }

    override fun stopLoading() {
        dialogView.hideProgressDialog()
    }

    override fun getResponses(data: HistoryDonasiResponse?) {
        if (data != null) {
            Log.e("get history donasi", data.donasi?.size.toString())
        }
        historyDonasiAdapter = HistoryDonasiAdapter(context!!, data?.donasi!!) {

            startActivity(intentFor<DetailHistoryDonasi>("donasi" to it))
        }
        val layoutManager = LinearLayoutManager(activity)

        rv_history_donasi.layoutManager = layoutManager
        rv_history_donasi.itemAnimator = DefaultItemAnimator()
        rv_history_donasi.adapter = historyDonasiAdapter
    }

}
