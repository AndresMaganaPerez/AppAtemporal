package com.example.appatemporal.framework.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.domain.models.EventsInMonth

class ActivityMainHomepageEspectadorAdapterHorizontal(private val list: MutableList<EventsInMonth>) : RecyclerView.Adapter<ActivityMainHomepageEspectadorViewHolderHorizontal>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityMainHomepageEspectadorViewHolderHorizontal {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ActivityMainHomepageEspectadorViewHolderHorizontal(layoutInflater.inflate(R.layout.activity_homepage_tarjeta_eventos_chica_espectador, parent, false))
    }

    override fun onBindViewHolder(holder: ActivityMainHomepageEspectadorViewHolderHorizontal, position: Int) {
        val item = list[position]
        Log.d("listLog", list.toString())
        holder.render(item)
    }

    override fun getItemCount(): Int = list.size
}