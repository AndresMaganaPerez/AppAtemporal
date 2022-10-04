package com.example.appatemporal.framework.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.databinding.ActivityFuncionPorEventoBinding
import com.example.appatemporal.domain.models.FuncionModel

class ActivityVisualizarEventoOrganizadorViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binding = ActivityFuncionPorEventoBinding.bind(view)

    fun render(funcionClass: FuncionModel){
        binding.NombreFuncion.text = "Funcion"
        binding.FechaFuncion.text = funcionClass.fecha_funcion
        binding.HoraInicioFuncion.text = funcionClass.hora_inicio
        binding.HoraFinFuncion.text = funcionClass.hora_fin
    }
}