package com.example.appatemporal.framework.view.adapters


import android.app.AlertDialog
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.data.localdatabase.entities.Objetivo
import com.example.appatemporal.data.localdatabase.entities.Proyecto
import com.example.appatemporal.databinding.ProyectosOrganizadorItemBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.view.ActivityProyectoOrganizador
import com.example.appatemporal.framework.view.ModificarProyecto
import com.example.appatemporal.framework.view.ProyectoOrganizador
import com.example.appatemporal.framework.viewModel.ProyectoOrganizadorViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProjectsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ProyectosOrganizadorItemBinding.bind(view)

    fun render(projectModel: Proyecto, viewModel: ProyectoOrganizadorViewModel) {
        binding.tvProjectName.text = projectModel.nombre_proyecto
        binding.ivEditIcon.setOnClickListener{
            val intent1 = Intent(itemView.context, ModificarProyecto::class.java)
            with(intent1){
                putExtra("id_proyecto", projectModel.id_proyecto)
                putExtra("nombre_proyecto", projectModel.nombre_proyecto)
                putExtra("fecha_inicio", projectModel.fecha_inicio)
            }
            itemView.context.startActivity(intent1)
        }

        binding.tvProjectName.setOnClickListener{
            val intent = Intent(itemView.context, ActivityProyectoOrganizador::class.java)
            with(intent){
                val viewModel = ProyectoOrganizadorViewModel()
                val repository = Repository(itemView.context)
                putExtra("pendingActivities", viewModel.countPendingActivities(repository, projectModel.id_proyecto, "No Completado"))
                putExtra("doneActivities", viewModel.countDoneActivities(repository, projectModel.id_proyecto, "Completado"))
                putExtra("totalActivities", viewModel.countAllActivities(repository, projectModel.id_proyecto))
                putExtra("id_proyecto", projectModel.id_proyecto)
                putExtra("nombre_proyecto", projectModel.nombre_proyecto)
                putExtra("fecha_inicio", projectModel.fecha_inicio)
                putExtra("ganancia_proyecto", projectModel.ganancia)
                putExtra("presupuesto_proyecto", projectModel.presupuesto)
                putExtra("meta_proyecto", projectModel.meta)
            }
            itemView.context.startActivity(intent)
        }


        binding.deleteProjectButton.setOnClickListener{
            val repository = Repository(itemView.context)
            val builder = AlertDialog.Builder(itemView.context)
            builder.setTitle("¿Estás seguro?")
            builder.setMessage("¿Estás seguro de que quieres eliminar este proyecto? Este proceso no puede revertirse")
            builder.setPositiveButton("Eliminar"){dialogInterface, which ->
                viewModel.removeProject(projectModel, repository)


            }

            builder.setNeutralButton("Cancelar"){dialogInterface , which ->

            }
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }
}