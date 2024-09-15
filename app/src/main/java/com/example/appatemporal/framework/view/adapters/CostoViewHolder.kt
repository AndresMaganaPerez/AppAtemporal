package com.example.appatemporal.framework.view.adapters


import android.app.AlertDialog
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.data.localdatabase.entities.Costo
import com.example.appatemporal.databinding.ItemCostoBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.view.AddCosto
import com.example.appatemporal.framework.view.DeleteCosto
import com.example.appatemporal.framework.view.ModificarCosto
import com.example.appatemporal.framework.viewModel.DeleteCostoViewModel
import kotlinx.android.synthetic.main.costo_task.view.*
import kotlinx.android.synthetic.main.dropdown_menu.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CostoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ItemCostoBinding.bind(view)

    fun render(costoModel: Costo){
        binding.txtShowTitle.text = costoModel.nombre_costo
        binding.ivEditIcon.setOnClickListener{
            val intent1 = Intent(itemView.context, ModificarCosto::class.java)
            with(intent1){
                putExtra("id_costo", costoModel.id_costo)
                putExtra("nombre_costo", costoModel.nombre_costo)
                putExtra("monto", costoModel.monto)
                putExtra("id_proyecto", costoModel.id_proyecto)
            }
            itemView.context.startActivity(intent1)
        }
            println(costoModel.monto)
        binding.txtShowMonto.setText(costoModel.monto.toString())
        binding.txtShowTitle.setOnClickListener{
            val intent = Intent(itemView.context, AddCosto::class.java)
            with(intent){
                putExtra("id_costo", costoModel.id_costo)
                putExtra("nombre_costo", costoModel.nombre_costo)
            }
            itemView.context.startActivity(intent)
        }


        binding.deleteCostoButton.setOnClickListener{
            val intent = Intent(itemView.context, DeleteCosto::class.java)
            val viewModel = DeleteCostoViewModel()

            val repository = Repository(itemView.context)
            val builder = AlertDialog.Builder(itemView.context)
            builder.setTitle("¿Estás seguro?")
            builder.setMessage("¿Estás seguro de que quieres eliminar este proveedor? Este proceso no puede revertirse")
            //builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Eliminar"){dialogInterface, which ->
                //Mandar a llamar la funcion delete()
                CoroutineScope(Dispatchers.IO ).launch {
                    viewModel.removeCosto(costoModel, repository)
                    //Toast.makeText(itemView.context, "Proyecto eliminado", Toast.LENGTH_SHORT).show()
                }
                itemView.context.startActivity(intent)


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