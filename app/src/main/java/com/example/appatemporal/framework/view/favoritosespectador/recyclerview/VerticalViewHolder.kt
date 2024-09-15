package com.example.appatemporal.framework.view.favoritosespectador.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.databinding.HomepageTarjetaEventosGrandeEspectadorBinding
import com.example.appatemporal.framework.view.favoritosespectador.TarjetaGrande_favoritosespectador

class VerticalViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = HomepageTarjetaEventosGrandeEspectadorBinding.bind(view)
        fun bind(item: TarjetaGrande_favoritosespectador){
            binding.Fecha.text = item.fecha
            binding.Descripcion.text = item.descripcion
            binding.Nombre.text=item.nombre

    }
}