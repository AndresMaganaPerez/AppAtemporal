package com.example.lab

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.framework.view.homepageespectador.TarjetaChica_espectador


class HorizontalViewHolder(view: View):RecyclerView.ViewHolder(view){

    val nombreArtista = view.findViewById<TextView>(R.id.nombreArtistaTarjetaChica)
    val fechaEvento = view.findViewById<TextView>(R.id.fechaTarjetaChica)
    val lugarEvento = view.findViewById<TextView>(R.id.lugarTarjetaChica)

    fun render(tarjetaChicaEspectadorModel: TarjetaChica_espectador){
        nombreArtista.text = tarjetaChicaEspectadorModel.nombreArtista
        fechaEvento.text = tarjetaChicaEspectadorModel.fecha
        lugarEvento.text = tarjetaChicaEspectadorModel.lugar
    }
}
