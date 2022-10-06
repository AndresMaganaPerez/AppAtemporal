package com.example.appatemporal.framework.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.appatemporal.R
import com.example.appatemporal.databinding.ActivityVisualizarEventoOrganizadorBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.GraphicsEventDetailViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.squareup.picasso.Picasso

class ActivityVisualizarEventoOrganizador : AppCompatActivity() {

    private lateinit var binding : ActivityVisualizarEventoOrganizadorBinding
    private val graphicsEventDetailViewModel : GraphicsEventDetailViewModel by viewModels()
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_evento_organizador)

        /* eventoIndividual.putExtra("idEvent", idEvent)
            eventoIndividual.putExtra("nombre", nombre)
            eventoIndividual.putExtra("direccion", direccion)
            eventoIndividual.putExtra("estado", estado)
            eventoIndividual.putExtra("ubicacion", ubicacion)
            eventoIndividual.putExtra("foto_portada", foto_portada) */

        binding = ActivityVisualizarEventoOrganizadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        binding.NombreEvento.text = intent.getStringExtra("nombre")
        binding.Ubicacion.text = intent.getStringExtra("ubicacion")
        binding.DireccionVEE.text = intent.getStringExtra("direccion")
        binding.CiudadEstadoVEE.text = intent.getStringExtra("estado")

        Picasso.get().load(intent.getStringExtra("foto_portada")).into(binding.ImagenVEE)

        binding.btnMoregraphics.setOnClickListener {
            var idEvent : String = intent.getStringExtra("idEvent").toString()

            val eventoIndividual =  Intent(this, DetailedMetrics::class.java)

            eventoIndividual.putExtra("idEvent", idEvent)
            this.startActivity(eventoIndividual)
        }

        //Creación de usuario temporal
        val tempEventId : String = "DM"
        repository = Repository(this)
        //Llamado a funciones
        var ventasTotal : Int = 0
        var asistenciasTotal : Int = 0
        graphicsEventDetailViewModel.getTicketsSA(tempEventId, repository)
        graphicsEventDetailViewModel.eventTicketsSAEvent.observe(this, Observer {
            ventasTotal = it.first
            asistenciasTotal = it.second
            populateTSAPieChart(ventasTotal, asistenciasTotal)
        })
        populateRating(tempEventId)
    }



    fun populateTSAPieChart(ventasTotal:Int,asistenciasTotal:Int){
        /*
        val ourPieChart = binding.tsaPieChart
        // Aqui se reciben los datos en teoria
        val ourPieEntry = ArrayList<PieEntry>()
        var noAssist = ventasTotal - asistenciasTotal
        ourPieEntry.add(PieEntry(noAssist.toFloat(), "No Asistieron"))
        ourPieEntry.add(PieEntry(asistenciasTotal.toFloat(), "Asistieron"))
        val ourSet = PieDataSet(ourPieEntry, "")
        val data = PieData(ourSet)
        // De aqui para abajo es formato
        val pieShades: ArrayList<Int> = ArrayList()
        pieShades.add(Color.parseColor("#FFBB86FC"))
        pieShades.add(Color.parseColor("#FE810E"))
        ourSet.colors = pieShades
        ourPieChart.data = data
        ourPieChart.invalidate()
        data.setValueTextColor(Color.DKGRAY)
        data.setValueTextSize(20f)
        ourPieChart.getLegend().setTextColor(Color.DKGRAY)
        ourPieChart.getDescription().setTextColor(Color.DKGRAY)
        ourPieChart.setEntryLabelColor(Color.DKGRAY)
        ourPieChart.description.isEnabled = false
        ourPieChart.setDrawEntryLabels(false)

         */
    }

    fun populateRating(eid:String){/*
        //Rating general del evento
        val ourRatingBar = binding.ratingStar
        val ourRatingValue = binding.ratingAvg
        val ourRatingCount = binding.ratingQuantity
        //Barra de 5 estrellas
        val ourRatingCount5 = binding.count5PB
        val ourRating5 = binding.count5TV
        //Barra de 4 estrellas
        val ourRatingCount4 = binding.count4PB
        val ourRating4 = binding.count4TV
        //Barra de 3 estrellas
        val ourRatingCount3 = binding.count3PB
        val ourRating3 = binding.count3TV
        //Barra de 2 estrellas
        val ourRatingCount2 = binding.count2PB
        val ourRating2 = binding.count2TV
        //Barra de 1 estrella
        val ourRatingCount1 = binding.count1PB
        val ourRating1 = binding.count1TV
        //Barra de 0 estrellas
        val ourRatingCount0 = binding.count0PB
        val ourRating0 = binding.count0TV
        //Creación de listas de los elementos con binding
        val ourRatingCountList = mutableListOf(ourRatingCount0,ourRatingCount1,
            ourRatingCount2,ourRatingCount3,ourRatingCount4,ourRatingCount5)
        val ourRatingList = mutableListOf(ourRating0, ourRating1, ourRating2,
            ourRating3, ourRating4,ourRating5)
        //Declaración del repositorio
        repository = Repository(this)
        //Llamada a la función y al observador para modificar elementos de Rating
        graphicsEventDetailViewModel.getExtEventRating(eid, repository)
        graphicsEventDetailViewModel.ratingExt.observe(this, Observer{
            ourRatingBar.rating = it[7]
            ourRatingValue.text = "${it[7]} de 5"
            ourRatingCount.text =  "${it[1].toInt()} calificaciones"
            //Incorporar información a progress bar varias
            for (i in 0..5) {
                if(it[i+2]>0){
                    ourRatingCountList[i].progress = ((it[i+2]*100)/it[1]).toInt()
                    ourRatingList[i].text = "${it[i+2].toInt()} votos"
                }else{
                    ourRatingCountList[i].progress = 0
                    ourRatingList[i].text = "0 votos"
                }
            }
        })*/
    }

}