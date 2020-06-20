package com.example.foodxdonatur.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodxdonatur.R
import com.example.foodxdonatur.model.KomunitasResponse

class KomunitasFragment : Fragment(), KomunitasView {

    private lateinit var komunitasPresenter: KomunitasPresenter
    private lateinit var komunitasAdapter: KomunitasAdapter
//    private var listkomunitas: MutableList<KomunitasResponse.Komunitas.User>



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
//        komunitasPresenter = KomunitasPresenter(this, this)
        komunitasPresenter.getKomunitas()
    }

    override fun isLoading() {
        TODO("Not yet implemented")
    }

    override fun stopLoading() {
        TODO("Not yet implemented")
    }

    override fun showKomunitas(data: KomunitasResponse?) {
        TODO("Not yet implemented")
    }

}
