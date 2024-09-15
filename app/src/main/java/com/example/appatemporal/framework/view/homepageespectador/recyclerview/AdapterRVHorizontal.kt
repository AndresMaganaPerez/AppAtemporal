package com.example.appatemporal.framework.view.homepageespectador.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.framework.view.homepageespectador.TarjetaChica_espectador

class AdapterRVHorizontal(private val data: List<TarjetaChica_espectador>):RecyclerView.Adapter<Horizontal_ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Horizontal_ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Horizontal_ViewHolder(layoutInflater.inflate(R.layout.activity_homepage_tarjeta_eventos_chica_espectador,parent,false))

    }
    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: Horizontal_ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

}