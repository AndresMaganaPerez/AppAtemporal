package com.example.lab

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.databinding.CardButtonAoBinding

class ViewHolderAO(view: View): RecyclerView.ViewHolder(view) {
    private val binding = CardButtonAoBinding.bind(view)
        fun bind(item: Card_button){
            binding.dato.text = item.dato
    }
}