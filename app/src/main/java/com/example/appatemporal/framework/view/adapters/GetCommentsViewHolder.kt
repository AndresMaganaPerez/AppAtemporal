package com.example.appatemporal.framework.view.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.databinding.ItemCommentBinding
import com.example.appatemporal.domain.models.CommentModel

class GetCommentsViewHolder (view: View): RecyclerView.ViewHolder(view) {

    val binding = ItemCommentBinding.bind(view)


    fun render(comment: CommentModel) {
        binding.comentario.text = comment.comentario
    }
}