package com.example.appatemporal.framework.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.domain.models.CategoryModel
import com.example.appatemporal.domain.models.EventModel

class EventosFiltradosAdapter(private val  eventsByCategory: List<EventModel>) : RecyclerView.Adapter<EventosFiltradosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventosFiltradosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EventosFiltradosViewHolder(layoutInflater.inflate(R.layout.activity_homepage_tarjetas_eventos_grande, parent, false))
    }

    override fun getItemCount(): Int {
        return eventsByCategory.size
    }

    override fun onBindViewHolder(holder: EventosFiltradosViewHolder, position: Int) {
        holder.bind(eventsByCategory[position])
    }
}
