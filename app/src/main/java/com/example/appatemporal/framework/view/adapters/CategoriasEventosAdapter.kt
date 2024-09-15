package com.example.appatemporal.framework.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.domain.models.CategoryModel


class CategoriasEventosAdapter(private val categoryList: List<CategoryModel>) : RecyclerView.Adapter<CategoriasEventosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriasEventosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CategoriasEventosViewHolder(layoutInflater.inflate(R.layout.categorias_eventos_item, parent, false))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoriasEventosViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }
}
