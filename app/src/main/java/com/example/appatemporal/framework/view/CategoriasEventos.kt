package com.example.appatemporal.framework.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appatemporal.databinding.CategoriasEventosBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.view.adapters.CategoriasEventosAdapter
import com.example.appatemporal.framework.view.adapters.EventosFiltradosAdapter
import com.example.appatemporal.framework.viewModel.CategoriasEventosViewModel

class CategoriasEventos: AppCompatActivity() {
    private lateinit var binding: CategoriasEventosBinding
    private val viewModel : CategoriasEventosViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CategoriasEventosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = Repository(this)

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
        viewModel.getCategories(repository)
        viewModel.categories.observe(this, Observer{ categoryList ->
            binding.RecyclerView.layoutManager = LinearLayoutManager(this)
            binding.RecyclerView.adapter = CategoriasEventosAdapter(categoryList)

        })



    }


}
