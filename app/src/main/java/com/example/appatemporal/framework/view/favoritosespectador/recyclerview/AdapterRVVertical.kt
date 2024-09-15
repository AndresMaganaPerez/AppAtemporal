package com.example.appatemporal.framework.view.favoritosespectador.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.framework.view.favoritosespectador.TarjetaGrande_favoritosespectador


class AdapterRVVertical(private val data: List<TarjetaGrande_favoritosespectador>): RecyclerView.Adapter<VerticalViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):VerticalViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VerticalViewHolder(layoutInflater.inflate(R.layout.homepage_tarjetas_eventos_grande,parent,false))
    }

    override fun getItemCount(): Int =  data.size

    override fun onBindViewHolder(holder: VerticalViewHolder, position: Int) {
            val item = data[position]
            holder.bind(item)
        }

}
