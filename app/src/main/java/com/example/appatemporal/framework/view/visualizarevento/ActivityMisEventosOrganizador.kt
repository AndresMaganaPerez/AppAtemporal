package com.example.appatemporal.framework.view

import android.content.Context
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


        val userUid = getSharedPreferences("userUid", Context.MODE_PRIVATE)
            .getString("userUid", "").toString()
        //val userIdTemp = "qVzK32OHDYOUtK1YsQbh"
        initRecyclerView(getOrganizerEventViewModel, userUid, repository)

        binding.addEventBtn.setOnClickListener{
            var userId : String = userUid

            val crearProyectoForm =  Intent(this, CreateEvent::class.java)

            crearProyectoForm.putExtra("userId", userId)

            this.startActivity(crearProyectoForm)
        }

        // ----------------------------Navbar------------------------------------
        val userRole = getSharedPreferences("user", Context.MODE_PRIVATE).getString("rol", "").toString()

        // Visibility
        if (userRole != "Organizador") {
            binding.navbar.budgetIcon.visibility = android.view.View.GONE
            binding.navbar.metricsIcon.visibility = android.view.View.GONE
            binding.navbar.budgetText.visibility = android.view.View.GONE
            binding.navbar.metricsText.visibility = android.view.View.GONE
        }
        if (userRole == "Ayudante") {
            binding.navbar.eventsIcon.visibility = android.view.View.GONE
            binding.navbar.eventsText.visibility = android.view.View.GONE
        }

        // Intents
        binding.navbar.homeIcon.setOnClickListener {
            if(userRole == "Organizador"){
                val intent = Intent(this, ActivityMainHomepageOrganizador::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, ActivityMainHomepageEspectador::class.java)
                startActivity(intent)
            }
        }

        binding.navbar.eventsIcon.setOnClickListener {
            if(userRole == "Organizador"){
                val intent = Intent(this,ActivityMisEventosOrganizador::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this,CategoriasEventos::class.java)
                startActivity(intent)
            }
        }

        binding.navbar.budgetIcon.setOnClickListener {
            val intent = Intent(this, ProyectoOrganizador::class.java)
            startActivity(intent)
        }

        binding.navbar.ticketsIcon.setOnClickListener {
            if (userRole == "Espectador" || userRole == "Organizador") {
                val intent = Intent(this, BoletoPorEventoActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, RegisterQRView::class.java)
                startActivity(intent)
            }
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