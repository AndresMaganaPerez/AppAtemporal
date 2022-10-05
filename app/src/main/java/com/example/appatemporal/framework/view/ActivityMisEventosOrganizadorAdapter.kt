package com.example.appatemporal.framework.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.domain.models.EventModel01

class ActivityMisEventosOrganizadorAdapter(private val list: MutableList<EventModel01>) : RecyclerView.Adapter<ActivityMisEventosOrganizadorViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityMisEventosOrganizadorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ActivityMisEventosOrganizadorViewHolder(layoutInflater.inflate(R.layout.activity_homepage_tarjeta_eventos_grande_espectador, parent, false))
    }

    override fun onBindViewHolder(holder: ActivityMisEventosOrganizadorViewHolder, position: Int) {
        val item = list[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = list.size


}