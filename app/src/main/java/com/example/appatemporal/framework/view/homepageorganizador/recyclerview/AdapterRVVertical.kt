package com.example.appatemporal.framework.view.homepageorganizador.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.framework.view.homepageespectador.TarjetaGrande_espectador
import com.example.lab.VerticalViewHolder


class AdapterRVVertical(private val tarjetaGrandeEspectadorList: List<TarjetaGrande_espectador>) : RecyclerView.Adapter<VerticalViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VerticalViewHolder(layoutInflater.inflate(R.layout.activity_homepage_tarjetas_eventos_grande, parent, false))
    }

    override fun onBindViewHolder(holder: VerticalViewHolder, position: Int) {
        val item = tarjetaGrandeEspectadorList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = tarjetaGrandeEspectadorList.size

}
