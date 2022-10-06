package com.example.appatemporal.framework.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appatemporal.databinding.ActivityBoletoPorEventoBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.view.adapters.boletosPorEventoAdapter
import com.example.appatemporal.framework.viewModel.GetUserTicketViewModel

class MyEventsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoletoPorEventoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val getTicketViewModel : GetUserTicketViewModel by viewModels()
        val repository = Repository(this)
        super.onCreate(savedInstanceState)
        binding = ActivityBoletoPorEventoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userIdTemp = "pod6xLDUeRNZItm7u93DC5CYbgJ2"
        initRecyclerView(getTicketViewModel, userIdTemp, repository)

        binding.navbar.homeIcon.setOnClickListener {
            finish()
        }

        binding.navbar.budgetIcon.setOnClickListener {
            val intent = Intent(this, ProyectoOrganizador::class.java)
            startActivity(intent)
        }

        binding.navbar.ticketsIcon.setOnClickListener {
            finish()
        }

        binding.navbar.metricsIcon.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

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