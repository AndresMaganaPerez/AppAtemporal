package com.example.appatemporal.framework.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.domain.models.EventModel
import com.example.appatemporal.domain.models.EventsInMonth

class ActivityMainHomepageOrganizadorAdapterHorizontal(private val list: MutableList<EventModel>) : RecyclerView.Adapter<ActivityMainHomepageOrganizadorViewHolderHorizontal>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityMainHomepageOrganizadorViewHolderHorizontal {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ActivityMainHomepageOrganizadorViewHolderHorizontal(layoutInflater.inflate(R.layout.activity_homepage_tarjeta_eventos_chica_espectador, parent, false))
    }

    override fun onBindViewHolder(holder: ActivityMainHomepageOrganizadorViewHolderHorizontal, position: Int) {
        val item = list[position]
        Log.d("listLogOrganizador", list.toString())
        holder.render(item)
    }

    override fun getItemCount(): Int = list.size
}