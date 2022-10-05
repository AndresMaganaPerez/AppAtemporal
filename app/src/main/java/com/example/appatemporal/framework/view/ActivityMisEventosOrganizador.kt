package com.example.appatemporal.framework.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appatemporal.databinding.ActivityBoletoPorEventoBinding
import com.example.appatemporal.databinding.ActivityMisEventosOrganizadorBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.GetOrganizerEventViewModel
import com.example.appatemporal.framework.viewModel.GetUserTicketViewModel
import kotlinx.android.synthetic.main.activity_boleto_por_evento.*

class ActivityMisEventosOrganizador : AppCompatActivity() {
    private lateinit var binding: ActivityMisEventosOrganizadorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val getOrganizerEventViewModel : GetOrganizerEventViewModel by viewModels()
        val repository = Repository(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMisEventosOrganizadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userIdTemp = "qVzK32OHDYOUtK1YsQbh"
        initRecyclerView(getOrganizerEventViewModel, userIdTemp, repository)

        binding.addEventBtn.setOnClickListener{
            /*val intent = Intent(this, ) // TODO: añadir la clase que llama al formulario de evento
            startActivity(intent)*/
        }

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

    private fun initRecyclerView(getOrganizerEventViewModel: GetOrganizerEventViewModel, userIdTemp: String, repository: Repository){
        getOrganizerEventViewModel.getOrganizerEvent(userIdTemp, repository)

        // Log.d("LOG Activity",getOrganizerEventViewModel.getOrganizerEvent(userIdTemp, repository).toString())
        getOrganizerEventViewModel.event.observe(this, Observer {
            binding.eventosOrganizadorRV.layoutManager = LinearLayoutManager(this) // Le da el layout que usará el RV.
            binding.eventosOrganizadorRV.adapter = ActivityMisEventosOrganizadorAdapter(it)
        })
    }

}