package com.example.appatemporal.framework.view.adapters

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.databinding.ActivityHomepageTarjetaEventosChicaEspectadorBinding
import com.example.appatemporal.databinding.ActivityHomepageTarjetaEventosGrandeEspectadorBinding
import com.example.appatemporal.domain.models.EventModel
import com.example.appatemporal.domain.models.EventsInMonth
import com.example.appatemporal.framework.view.ActivityVisualizarEventoEspectador
import com.example.appatemporal.framework.view.ActivityVisualizarEventoOrganizador
import com.example.appatemporal.framework.view.ConsultarBoleto
import com.squareup.picasso.Picasso

class ActivityMainHomepageEspectadorViewHolderVertical(view: View) : RecyclerView.ViewHolder(view){
    val binding = ActivityHomepageTarjetaEventosGrandeEspectadorBinding.bind(view)

    fun render(eventModel: EventModel){
        binding.Nombre.text = eventModel.nombre
        binding.Lugar.text = eventModel.ubicacion
        binding.Direccion.text = eventModel.direccion
        Picasso.get().load(eventModel.foto_portada).into(binding.imageCard)

        var cardEventBtn = binding.cardEvent

        cardEventBtn.setOnClickListener {
            var idEvent : String = eventModel.id
            var nombre : String = eventModel.nombre
            var direccion : String = eventModel.direccion
            var estado :String = eventModel.ciudad+", "+eventModel.estado
            var ubicacion :String = eventModel.ubicacion
            var foto_portada : String = eventModel.foto_portada

            /*var idEvent : String = eventsInMonth.idEvent
            var nombre : String = eventsInMonth.nombreEvento
            var direccion : String = eventsInMonth.direccion
            var estado :String = eventsInMonth.ciudad+", "+eventsInMonth.estado
            var lugar : String = eventsInMonth.lugarEvento
            var foto_portada : String = eventsInMonth.urlImagen*/

            val eventoIndividual =  Intent(itemView.context, ActivityVisualizarEventoEspectador::class.java)

            eventoIndividual.putExtra("idEvent", idEvent)
            eventoIndividual.putExtra("nombre", nombre)
            eventoIndividual.putExtra("direccion", direccion)
            eventoIndividual.putExtra("estado", estado)
            eventoIndividual.putExtra("ubicacion", ubicacion)
            eventoIndividual.putExtra("foto_portada", foto_portada)

            itemView.context.startActivity(eventoIndividual)
        }
    }

}