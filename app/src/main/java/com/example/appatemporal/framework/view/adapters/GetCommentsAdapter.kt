package com.example.appatemporal.framework.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.domain.models.CommentModel

class GetCommentsAdapter(private val listaComentarios: MutableList<CommentModel>) : RecyclerView.Adapter<GetCommentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetCommentsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return GetCommentsViewHolder(layoutInflater.inflate(R.layout.item_comment, parent, false))
    }

    override fun onBindViewHolder(holder: GetCommentsViewHolder, position: Int) {
        val item = listaComentarios[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = listaComentarios.size

}