package com.example.lab

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R


class AdapterAO(private val data: List<Card_button>): RecyclerView.Adapter<ViewHolderAO>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolderAO {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolderAO(layoutInflater.inflate(R.layout.card_button_ao,parent,false))
    }

    override fun getItemCount(): Int =  data.size

    override fun onBindViewHolder(holder: ViewHolderAO, position: Int) {
        val item = data[position]
        holder.bind(item)
    }
}