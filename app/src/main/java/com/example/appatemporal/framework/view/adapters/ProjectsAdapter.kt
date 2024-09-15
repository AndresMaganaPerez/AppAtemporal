package com.example.appatemporal.framework.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.data.localdatabase.entities.Objetivo
import com.example.appatemporal.data.localdatabase.entities.Proyecto
import com.example.appatemporal.framework.viewModel.ProyectoOrganizadorViewModel


class ProjectsAdapter(private val projectsList: List<Proyecto>, private val viewModel: ProyectoOrganizadorViewModel):
    RecyclerView.Adapter<ProjectsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProjectsViewHolder(layoutInflater.inflate(R.layout.proyectos_organizador_item, parent, false))
    }

    override fun onBindViewHolder(holder: ProjectsViewHolder, position: Int) {
        val item = projectsList[position]
        holder.render(item, viewModel)
    }

    override fun getItemCount(): Int = projectsList.size



}