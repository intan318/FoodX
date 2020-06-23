package com.example.foodxdonatur.komunitas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodxdonatur.R
import com.example.foodxdonatur.model.KomunitasResponse
import kotlinx.android.synthetic.main.card_komunitas.view.*

class KomunitasAdapter(
    private val context: Context,
    private var komunitaslist: List<KomunitasResponse.Komunitas>,
    val listener: (KomunitasResponse.Komunitas) -> Unit
) :
    RecyclerView.Adapter<KomunitasAdapter.ViewHolder>()
    {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
           return ViewHolder(
               LayoutInflater.from(context).inflate(
                   R.layout.card_komunitas,
                   parent,
                   false
               )
           )
        }


        override fun getItemCount(): Int {
            return komunitaslist.size
        }

        override fun onBindViewHolder(holder: KomunitasAdapter.ViewHolder, position: Int) {
            holder.binding(context = context, komunitas = komunitaslist[position], position = position)
        }

        inner class ViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {
            fun binding(context: Context, komunitas: KomunitasResponse.Komunitas, position: Int){

                containerView.txtNamaKomunitas.text = komunitas.user?.name.toString()
                containerView.txtLokasiKomunitas.text = komunitas.user?.alamat.toString()
//                containerView.imgKomunitas.let {
//                    Glide.with(context).load(komunitas.komunitas.fotoKomunitas).centerCrop().into(it)
//                }

                containerView.setOnClickListener{
                    listener(komunitas)
                }
            }

        }

    }