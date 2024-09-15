package com.example.appatemporal.framework.view.homepageorganizador.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.framework.view.homepageespectador.TarjetaChica_espectador
import com.example.lab.HorizontalViewHolder

class AdapterRVHorizontal(private val data:List<TarjetaChica_espectador>) : RecyclerView.Adapter<HorizontalViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HorizontalViewHolder(layoutInflater.inflate(R.layout.activity_homepage_tarjeta_eventos_chica,parent,false))

    }

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
        val item = data[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = data.size
}