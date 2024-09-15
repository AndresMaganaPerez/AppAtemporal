package com.example.appatemporal.framework.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.appatemporal.R
import com.example.appatemporal.data.localdatabase.entities.Objetivo
import com.example.appatemporal.databinding.ActivityProyectoOrganizadorBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.ProyectoOrganizadorViewModel
import kotlinx.coroutines.launch

class ActivityProyectoOrganizador: AppCompatActivity() {
    private val viewModel: ProyectoOrganizadorViewModel by viewModels()
    private lateinit var binding: ActivityProyectoOrganizadorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProyectoOrganizadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var myExtras :Bundle? = intent.extras
        val stringToDo = "Actividades por completar: ".plus(myExtras?.getInt("pendingActivities"))
        val stringCompleted = "Actividades completadas: ".plus(myExtras?.getInt("doneActivities"))

        binding.activitiesCompleted.text = stringToDo
        binding.activitiesToDo.text = stringCompleted
        binding.line2.text = myExtras?.getString("fecha_inicio")
        binding.projectName.text = myExtras?.getString("nombre_proyecto")
        var idProyecto:Int =  myExtras?.getInt("id_proyecto")?:-1
        var ganancia:Double = (myExtras?.getDouble("ganancia_proyecto")?:-1) as Double
        var presupuesto:Double = (myExtras?.getDouble("presupuesto_proyecto")?:-1) as Double
        var meta:Double = (myExtras?.getDouble("meta_proyecto")?:-1) as Double



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


        binding.bottomObjective.setOnClickListener {

            val intent = Intent(this, PresupuestoAndMeta::class.java)
            with(intent){
                putExtra("id_proyecto", idProyecto)
                putExtra("gananciaK", ganancia)
                putExtra("presupuestoK", presupuesto)
                putExtra("metaK", meta)
            }
            startActivity(intent)
        }
        binding.bottomActivity.setOnClickListener {

            val intent = Intent(this, DeleteActivity::class.java)
            with(intent){
                putExtra("id_proyecto", idProyecto)
            }
            Log.d("prueba", idProyecto.toString())
            startActivity(intent)
        }

        binding.buttonCostos.setOnClickListener{
            val intent = Intent(this, DeleteCosto::class.java)
            with(intent){
                putExtra("id_proyecto", idProyecto)
            }
            startActivity(intent)
        }
    }
}
