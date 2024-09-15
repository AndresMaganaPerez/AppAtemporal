package com.example.appatemporal.framework.view.adapters


import android.app.AlertDialog
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.data.localdatabase.entities.Actividad
import com.example.appatemporal.databinding.ItemTodoBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.view.AddActivity
import com.example.appatemporal.framework.view.DeleteActivity
import com.example.appatemporal.framework.view.ModificarActividad
import com.example.appatemporal.framework.viewModel.DeleteActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ActividadViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ItemTodoBinding.bind(view)

    fun render(activityModel: Actividad, viewModel: DeleteActivityViewModel) {
        binding.txtShowTitle.text = activityModel.nombre_actividad
        binding.txtShowArea.text = activityModel.area
        binding.txtShowEstatus.text = activityModel.estatus
        binding.txtShowPrioridad.text = activityModel.prioridad
        binding.ivEditIcon.setOnClickListener{
            val intent1 = Intent(itemView.context, ModificarActividad::class.java)
            with(intent1){
                putExtra("id_actividad", activityModel.id_actividad)
                putExtra("nombre_actividad", activityModel.nombre_actividad)
                putExtra("area", activityModel.area)
                putExtra("estatus", activityModel.estatus)
                putExtra("prioridad", activityModel.prioridad)
                putExtra("id_proyecto", activityModel.id_proyecto)
            }
            itemView.context.startActivity(intent1)
        }


        binding.deleteActivityButton.setOnClickListener{

            val repository = Repository(itemView.context)
            val builder = AlertDialog.Builder(itemView.context)
            builder.setTitle("¿Estás seguro?")
            builder.setMessage("¿Estás seguro de que quieres eliminar esta actividad Este proceso no puede revertirse")
            //builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Eliminar"){dialogInterface, which ->
                //Mandar a llamar la funcion delete()
                    viewModel.removeActividad(activityModel.id_proyecto,activityModel, repository)
                    //Toast.makeText(itemView.context, "Proyecto eliminado", Toast.LENGTH_SHORT).show()


                //Toast.makeText(itemView.context,"Se eliminó el proyecto correctamente",Toast.LENGTH_LONG).show()
            }
            builder.setNeutralButton("Cancelar"){dialogInterface , which ->

            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }
}