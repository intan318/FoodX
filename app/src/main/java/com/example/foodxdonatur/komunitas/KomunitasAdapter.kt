package com.example.foodxdonatur.komunitas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodxdonatur.R
import com.example.foodxdonatur.model.KomunitasResponse
import kotlinx.android.synthetic.main.card_komunitas.view.*

class KomunitasAdapter(
    private val context: Context,
    val listener: (KomunitasResponse.Komunitas.User) -> Unit
) :
    RecyclerView.Adapter<KomunitasAdapter.ViewHolder>()
    {
        private var komunitaslist: MutableList<KomunitasResponse.Komunitas.User> = mutableListOf()

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

        fun setData(list: MutableList<KomunitasResponse.Komunitas.User>){
            komunitaslist = list
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return komunitaslist.size
        }

        override fun onBindViewHolder(holder: KomunitasAdapter.ViewHolder, position: Int) {
            holder.binding(context = context, komunitas = komunitaslist[position], position = position)
        }

        inner class ViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {
            fun binding(context: Context, komunitas: KomunitasResponse.Komunitas.User, position: Int){

                containerView.txtNamaKomunitas.text = komunitas.name
                containerView.txtLokasiKomunitas.text = komunitas.alamat
//                containerView.imgKomunitas.let {
//                    Glide.with(context).load(komunitas.images!![0]?.imageUrl).centerCrop().into(it)
//                }

                containerView.setOnClickListener{
                    listener(komunitas)
                }
            }

        }

    }