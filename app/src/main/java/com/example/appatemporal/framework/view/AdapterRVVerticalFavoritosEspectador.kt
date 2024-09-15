package com.example.appatemporal.framework.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R


class AdapterRVVerticalFavoritosEspectador(private val data: List<TarjetaGrande_favoritosespectador>): RecyclerView.Adapter<VerticalViewHolderFavoritosEspectador>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalViewHolderFavoritosEspectador {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VerticalViewHolderFavoritosEspectador(layoutInflater.inflate(R.layout.activity_homepage_tarjetas_eventos_grande,parent,false))
    }

    override fun getItemCount(): Int =  data.size

    override fun onBindViewHolder(holder: VerticalViewHolderFavoritosEspectador, position: Int) {
            val item = data[position]
            holder.bind(item)
        }

}
