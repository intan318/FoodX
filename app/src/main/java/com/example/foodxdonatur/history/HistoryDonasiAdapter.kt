package com.example.foodxdonatur.history

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodxdonatur.R
import com.example.foodxdonatur.komunitas.KomunitasAdapter
import com.example.foodxdonatur.model.HistoryDonasiResponse
import com.example.foodxdonatur.model.KomunitasResponse
import com.example.foodxdonatur.network.APIFactory
import kotlinx.android.synthetic.main.card_history_donasi.view.*
import kotlinx.android.synthetic.main.card_komunitas.view.*

class HistoryDonasiAdapter (
    private val context: Context,
    private var donasilist: List<HistoryDonasiResponse.Donasi>,
    val listener: (HistoryDonasiResponse.Donasi) -> Unit
) :
    RecyclerView.Adapter<HistoryDonasiAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.card_history_donasi,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return donasilist.size
    }

    override fun onBindViewHolder(holder: HistoryDonasiAdapter.ViewHolder, position: Int) {
        holder.binding(context= context, donasi = donasilist[position], position = position)
    }

    inner class ViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView){
        fun binding(context: Context, donasi: HistoryDonasiResponse.Donasi, position: Int){

            containerView.txtTglDonasi.text = donasi.tglPenjemputan.toString()
            containerView.txtAlamatDonasi.text = donasi.alamatPenjemputan.toString()

            val photo = APIFactory.BASE_URL_IMAGE+donasi.foto!!

            Log.e("Cek foto", photo.toString())
            containerView.imgDonasi.let {
                Glide.with(context).load(photo).into(it)
            }

            containerView.setOnClickListener{
                listener(donasi)
            }
        }
    }

}