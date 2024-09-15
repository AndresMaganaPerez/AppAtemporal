package com.example.appatemporal.framework.view.homepageespectador.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.framework.view.homepageespectador.TarjetaGrande_espectador


class AdapterRVVertical(private val data: List<TarjetaGrande_espectador>): RecyclerView.Adapter<Vertical_ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vertical_ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Vertical_ViewHolder(layoutInflater.inflate(R.layout.activity_homepage_tarjeta_eventos_grande_espectador,parent,false))
    }

    override fun getItemCount(): Int =  data.size

    override fun onBindViewHolder(holder: Vertical_ViewHolder, position: Int) {
            val item = data[position]
            holder.bind(item)
        }

}
