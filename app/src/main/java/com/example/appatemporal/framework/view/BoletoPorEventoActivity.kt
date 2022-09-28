package com.example.appatemporal.framework.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.databinding.ActivityBoletoPorEventoBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.GetUserTicketViewModel

class BoletoPorEventoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoletoPorEventoBinding
    //var binding: ActivityBoletoPorEventoBinding = ActivityBoletoPorEventoBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        val getTicketViewModel : GetUserTicketViewModel by viewModels()
         val repository = Repository(this)
        super.onCreate(savedInstanceState)
        binding = ActivityBoletoPorEventoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userIdTemp = "pod6xLDUeRNZItm7u93DC5CYbgJ2"
        initRecyclerView(getTicketViewModel, userIdTemp, repository)
    }

    private fun initRecyclerView(getTicketViewModel: GetUserTicketViewModel, userIdTemp: String, repository: Repository){
        getTicketViewModel.getUserTicket(userIdTemp, repository)
        Log.d("LOG Activity",getTicketViewModel.getUserTicket(userIdTemp, repository).toString())
        getTicketViewModel.ticket.observe(this, Observer {
            binding.boletosRecyclerView.layoutManager = LinearLayoutManager(this) // Le da el layout que usará el RV.
            binding.boletosRecyclerView.adapter = boletosPorEventoAdapter(it)
        })
    }

}