package com.example.appatemporal.framework.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.domain.models.GetTicketModel

class boletosPorEventoAdapter (private val list: MutableList<GetTicketModel>) : RecyclerView.Adapter<boletosPorEventoViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): boletosPorEventoViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return boletosPorEventoViewHolder(layoutInflater.inflate(R.layout.activity_card_boleto_por_evento, parent, false))
        }

        // Este método pasa por cada elemento de la lista mandándolo al render del viewholder.
        override fun onBindViewHolder(holder: boletosPorEventoViewHolder, position: Int) {
            val item = list[position]
            holder.render(item)
        }



        override fun getItemCount(): Int = list.size
}