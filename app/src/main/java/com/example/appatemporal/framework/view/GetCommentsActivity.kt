package com.example.appatemporal.framework.view

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appatemporal.databinding.ActivityGetcommentsBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.CommentModel
import com.example.appatemporal.framework.view.adapters.GetCommentsAdapter
import com.example.appatemporal.framework.viewModel.GetCommentsViewModel

class GetCommentsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityGetcommentsBinding
    private val getCommentsViewModel: GetCommentsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = Repository(this)
        binding = ActivityGetcommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idEvento = "12hEWP8xQQgQGjCyuWon"

        getCommentsViewModel.getComments(idEvento, repository)

        getCommentsViewModel.commentList.observe(this, Observer {
            initRecyclerView(it)
        })
    }

    fun initRecyclerView(commentList: MutableList<CommentModel>){
        val manager = LinearLayoutManager(this)
        binding.recyclerComment.layoutManager = manager

        binding.recyclerComment.adapter = GetCommentsAdapter(commentList)

    }


}