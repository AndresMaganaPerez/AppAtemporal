package com.example.appatemporal.framework.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.databinding.ActivityHomepageTarjetaEventosGrandeEspectadorBinding

class VerticalViewHolderFavoritosEspectador(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ActivityHomepageTarjetaEventosGrandeEspectadorBinding.bind(view)
        fun bind(item: TarjetaGrande_favoritosespectador){
            binding.Lugar.text = item.fecha
            binding.Direccion.text = item.descripcion
            binding.Nombre.text=item.nombre

    }
}