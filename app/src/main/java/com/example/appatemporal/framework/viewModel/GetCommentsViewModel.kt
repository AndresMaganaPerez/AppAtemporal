package com.example.appatemporal.framework.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.GetCommentsRequirement
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.CommentModel
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.util.*

class GetCommentsViewModel :ViewModel() {
    private val getCommentsRequirement = GetCommentsRequirement()

    val commentList = MutableLiveData<MutableList<CommentModel>>()

    fun getComments(idEvent: String, repository: Repository) {
        viewModelScope.launch {
            val queryResult = getCommentsRequirement(idEvent, repository)
            val auxList = mutableListOf<CommentModel>()
            for (document in queryResult) {
                var auxDate = document.data.get("fecha_creacion") as com.google.firebase.Timestamp
                var commentAux = CommentModel(document.data.get("id_usuario_fk").toString(), document.data.get("id_evento_fk").toString(),
                                                document.data.get("comentario").toString(), auxDate.toDate())
                auxList.add(commentAux)
            }
            commentList.postValue(auxList)
        }
    }
}