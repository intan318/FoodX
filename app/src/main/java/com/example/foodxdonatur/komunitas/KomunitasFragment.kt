package com.example.foodxdonatur.komunitas

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodxdonatur.R
import com.example.foodxdonatur.model.KomunitasResponse
import com.example.foodxdonatur.utils.DialogView
import com.example.foodxdonatur.utils.SessionManager
import kotlinx.android.synthetic.main.fragment_komunitas.*
import org.jetbrains.anko.support.v4.intentFor

class KomunitasFragment : Fragment(), KomunitasView {

    private lateinit var dialogView: DialogView
    private lateinit var komunitasPresenter: KomunitasPresenter
    private lateinit var komunitasAdapter: KomunitasAdapter
//    private var listkomunitas: List<KomunitasResponse.Komunitas>

    val idKomunitas = Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_komunitas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogView = DialogView(context!! as Activity)
        komunitasPresenter = KomunitasPresenter(context!!, this)
        komunitasPresenter.getKomunitas(token = SessionManager.getInstance(context!!).getToken()!!)
    }

    override fun isLoading() {
        dialogView.showProgressDialog()
    }

    override fun stopLoading() {
        dialogView.hideProgressDialog()
    }

    override fun showKomunitas(data: KomunitasResponse?) {
        if (data != null) {
            Log.e("get komunitas", data.komunitas.toString())
        }
        komunitasAdapter = KomunitasAdapter(context!!, data?.komunitas!!) {

            startActivity(intentFor<DetailKomunitasActivity>("komunitas" to it))
        }
        val layoutManager = LinearLayoutManager(activity)

        rv_komunitas.layoutManager = layoutManager
        rv_komunitas.itemAnimator = DefaultItemAnimator()
        rv_komunitas.adapter = komunitasAdapter
    }

}
