package com.example.appatemporal.framework.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.appatemporal.R
import com.example.appatemporal.databinding.DashboardBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.*
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class Dashboard : AppCompatActivity(){
    private lateinit var binding : DashboardBinding
    private val dashboardViewModel : DashboardViewModel by viewModels()
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        // En el onCreate se deben poblar las graficas
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)

        val userUid = getSharedPreferences("user", Context.MODE_PRIVATE)
            .getString("userUid", "").toString()

        binding = DashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        val uid = getSharedPreferences("user", Context.MODE_PRIVATE)
            .getString("userUid", "").toString()
        //Usuario temporal de pruebas, eliminar posteriormente
        val tempUserId : String = "qVzK32OHDYOUtK1YsQbh"
        repository = Repository(this)

        //Inicia llamado a funciones
        //Grafica1 - Ganancias totales y cantidad de eventos totales
        populateEventCount(tempUserId)
        //Grafica2 - Asistentes esperados totales vs. Asistentes reales totales
        var ventasTotal : Int = 0
        var asistenciasTotal : Int = 0
        dashboardViewModel.ventasEvent(tempUserId, repository)
        dashboardViewModel.ventas.observe(this, Observer{
            ventasTotal = it.first
            asistenciasTotal = it.second
            populatePieChart(ventasTotal, asistenciasTotal)
        })
        //Grafica3 - Rating promedio de todos los eventos
        populateRating(tempUserId)
    }

    private fun populateEventCount(uid:String) {
        val ourRevenue = binding.eventRevenue
        val ourEventCount = binding.eventCountTotal
        repository = Repository(this)
        dashboardViewModel.getRevenue(uid, repository)
        dashboardViewModel.revenue.observe(this, Observer{
            val profit: String = NumberFormat.getNumberInstance(Locale.US).format(it)
            ourRevenue.text = "$${profit} MXN"
        })
        dashboardViewModel.countEvent(uid, repository)
        dashboardViewModel.count.observe(this, Observer {
            if (it == 1){
                ourEventCount.text = "En ${it} evento"
            } else {
                ourEventCount.text = "En ${it} eventos"
            }
        })
    }

    private fun populatePieChart(ventasTotal: Int, asistenciasTotal : Int) {
        //ingreso de los datos a la pie chart
        val ourPieChart = binding.dashPieChart
        val ourPieEntry = ArrayList<PieEntry>()
        var noAssist = ventasTotal - asistenciasTotal
        ourPieEntry.add(PieEntry(noAssist.toFloat(), "Asistentes esperados"))
        ourPieEntry.add(PieEntry(asistenciasTotal.toFloat(), "Asistentes reales"))
        val ourSet = PieDataSet(ourPieEntry, "")
        val data = PieData(ourSet)
        //formato de la pie chart
        val pieShades: ArrayList<Int> = ArrayList()
        pieShades.add(Color.parseColor("#FFBB86FC"))
        pieShades.add(Color.parseColor("#FE810E"))
        ourSet.colors = pieShades
        ourPieChart.data = data
        ourPieChart.data.setValueFormatter(DefaultValueFormatter(0))
        data.setValueTextColor(Color.DKGRAY)
        data.setValueTextSize(20f)
        ourPieChart.getLegend().setTextColor(Color.DKGRAY)
        ourPieChart.getDescription().setTextColor(Color.DKGRAY)
        ourPieChart.setEntryLabelColor(Color.DKGRAY)
        ourPieChart.description.isEnabled = false
        ourPieChart.setDrawEntryLabels(false)
        ourPieChart.invalidate()
    }

    private fun populateRating(uid:String){
        val ourRatingBar = binding.ratingStars
        val ourRatingValue = binding.ratingAvg
        repository = Repository(this)
        dashboardViewModel.getRating(uid, repository)
        dashboardViewModel.rating.observe(this, Observer{
            ourRatingBar.rating = it
            ourRatingValue.text = "Rating promedio: ${it}"
        })
    }
}