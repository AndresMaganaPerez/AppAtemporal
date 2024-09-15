package com.example.lab

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.framework.view.homepageespectador.TarjetaGrande_espectador

class VerticalViewHolder(view: View):RecyclerView.ViewHolder(view){

    val nombreArtista = view.findViewById<TextView>(R.id.nombreTarjetaGrande)
    val fechaEvento = view.findViewById<TextView>(R.id.fechaTarjetaGrande)
    val descripcionEvento = view.findViewById<TextView>(R.id.descripcionTarjetaGrande)

    fun render(tarjetaGrandeEspectador: TarjetaGrande_espectador){
        nombreArtista.text = tarjetaGrandeEspectador.nombre
        fechaEvento.text = tarjetaGrandeEspectador.fecha
        descripcionEvento.text = tarjetaGrandeEspectador.descripcion
    }
}
