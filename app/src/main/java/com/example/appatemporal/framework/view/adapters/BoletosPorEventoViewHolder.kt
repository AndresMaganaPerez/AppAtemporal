package com.example.appatemporal.framework.view.adapters

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.databinding.ActivityCardBoletoPorEventoBinding
import com.example.appatemporal.domain.models.GetTicketModel
import com.example.appatemporal.framework.view.ConsultarBoleto

class boletosPorEventoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ActivityCardBoletoPorEventoBinding.bind(view)


    fun render(boletoClass: GetTicketModel){
        binding.Nombre.text = boletoClass.nombre_evento
        binding.Fecha.text = boletoClass.fecha
        binding.Hora.text = boletoClass.horario

        var cardBtn = binding.cardBoletoEvento

        cardBtn.setOnClickListener{
            var Nombre : String = boletoClass.nombre_evento
            var Fecha : String = boletoClass.fecha
            var Hora : String = boletoClass.horario
            var Lugar : String = boletoClass.lugar
            var Direccion : String = boletoClass.direccion
            var Ciudad : String = boletoClass.ciudad
            var Estado : String = boletoClass.estado
            var HashQR : String = boletoClass.hash_qr


            val boletoIndividual =  Intent(itemView.context, ConsultarBoleto::class.java)

            boletoIndividual.putExtra("nombre", Nombre)
            boletoIndividual.putExtra("fecha", Fecha)
            boletoIndividual.putExtra("hora", Hora)
            boletoIndividual.putExtra("lugar", Lugar)
            boletoIndividual.putExtra("direccion", Direccion)
            boletoIndividual.putExtra("ciudad", Ciudad)
            boletoIndividual.putExtra("estado", Estado)
            boletoIndividual.putExtra("hashQr", HashQR)
            boletoIndividual.putExtra("idEvento", boletoClass.id_evento)

            itemView.context.startActivity(boletoIndividual)
        }
    }
}