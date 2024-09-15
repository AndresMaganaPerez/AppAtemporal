package com.example.appatemporal.framework.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.domain.models.FuncionModel

class ActivityVisualizarEventoOrganizadorAdapter(private val list: MutableList<FuncionModel>) : RecyclerView.Adapter<ActivityVisualizarEventoOrganizadorViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityVisualizarEventoOrganizadorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ActivityVisualizarEventoOrganizadorViewHolder(layoutInflater.inflate(R.layout.activity_funcion_por_evento, parent, false))
    }

    override fun onBindViewHolder(holder: ActivityVisualizarEventoOrganizadorViewHolder, position: Int) {
        val item = list[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = list.size

}