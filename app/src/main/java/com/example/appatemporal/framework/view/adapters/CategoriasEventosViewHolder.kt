package com.example.appatemporal.framework.view.adapters

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.databinding.CategoriasEventosItemBinding
import com.example.appatemporal.domain.models.CategoryModel
import com.example.appatemporal.framework.view.EventosFiltrados
import kotlinx.android.synthetic.main.categorias_eventos_item.view.*
import java.util.*


class CategoriasEventosViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = CategoriasEventosItemBinding.bind(view)

    fun bind(categoria: CategoryModel) {
        with(itemView) {
            val colors = resources.getIntArray(R.array.random_color)
            val randomColor = colors[Random().nextInt(colors.size)]

            val shape = GradientDrawable()
            shape.cornerRadius = 38F
            shape.setColor(randomColor)
            tvCategoria.background = shape
        }
        binding.tvCategoria.text = categoria.nombre

        binding.tvCategoria.setOnClickListener{
            val intent = Intent(itemView.context, EventosFiltrados::class.java)
            with(intent){
                putExtra("categoria_nombre", categoria.nombre)
            }
            itemView.context.startActivity(intent)
        }
    }


}