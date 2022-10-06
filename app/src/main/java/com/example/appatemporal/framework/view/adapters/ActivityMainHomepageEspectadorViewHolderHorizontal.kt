package com.example.appatemporal.framework.view.adapters

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.databinding.ActivityHomepageTarjetaEventosChicaEspectadorBinding
import com.example.appatemporal.databinding.ActivityHomepageTarjetaEventosGrandeEspectadorBinding
import com.example.appatemporal.domain.models.EventsInMonth
import com.example.appatemporal.framework.view.ActivityVisualizarEventoEspectador
import com.example.appatemporal.framework.view.ActivityVisualizarEventoOrganizador
import com.squareup.picasso.Picasso
import com.example.appatemporal.framework.view.ConsultarBoleto

class ActivityMainHomepageEspectadorViewHolderHorizontal(view: View) : RecyclerView.ViewHolder(view){
    val binding = ActivityHomepageTarjetaEventosChicaEspectadorBinding.bind(view)

    fun render(eventsInMonth: EventsInMonth){
        binding.nombreArtista.text = eventsInMonth.nombreEvento
        binding.fecha.text = eventsInMonth.lugarEvento
        binding.lugar.text = eventsInMonth.fecha+" - "+eventsInMonth.horaInicioEvento
        Picasso.get().load(eventsInMonth.urlImagen).into(binding.imagenSmallCard)

        var cardEventBtn = binding.cardBtnChica

        cardEventBtn.setOnClickListener {
            var idEvent : String = eventsInMonth.idEvent
            var nombre : String = eventsInMonth.nombreEvento
            var direccion : String = eventsInMonth.direccion
            var estado :String = eventsInMonth.ciudad + ", " + eventsInMonth.estado
            var lugar : String = eventsInMonth.lugarEvento
            var foto_portada : String = eventsInMonth.urlImagen

            val eventoIndividual =  Intent(itemView.context, ActivityVisualizarEventoEspectador::class.java)

            eventoIndividual.putExtra("idEvent", idEvent)
            eventoIndividual.putExtra("nombre", nombre)
            eventoIndividual.putExtra("direccion", direccion)
            eventoIndividual.putExtra("estado", estado)
            eventoIndividual.putExtra("ubicacion", lugar)
            eventoIndividual.putExtra("foto_portada", foto_portada)

            itemView.context.startActivity(eventoIndividual)
        }
    }

}