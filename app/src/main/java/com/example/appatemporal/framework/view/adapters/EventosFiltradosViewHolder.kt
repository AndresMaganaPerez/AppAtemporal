package com.example.appatemporal.framework.view.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.databinding.ActivityHomepageTarjetasEventosGrandeBinding
import com.example.appatemporal.databinding.CategoriasEventosItemBinding
import com.example.appatemporal.domain.models.CategoryModel
import com.example.appatemporal.domain.models.EventModel
import com.squareup.picasso.Picasso

class EventosFiltradosViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ActivityHomepageTarjetasEventosGrandeBinding.bind(view)

    fun bind(evento: EventModel) {

        binding.nombreTarjetaGrande.text = evento.nombre
        binding.fechaTarjetaGrande.text = evento.ubicacion
        binding.descripcionTarjetaGrande.text = evento.descripcion
        Picasso.get().load(evento.foto_portada).into(binding.imageView)

    }

}