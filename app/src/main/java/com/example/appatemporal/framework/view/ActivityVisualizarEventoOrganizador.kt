package com.example.appatemporal.framework.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appatemporal.R
import com.example.appatemporal.databinding.ActivityAddCategoriaBinding
import com.example.appatemporal.databinding.ActivityVisualizarEventoOrganizadorBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.GetFunctionOrganizerViewModel
import com.example.appatemporal.framework.viewModel.GraphicsEventDetailViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.squareup.picasso.Picasso

class ActivityVisualizarEventoOrganizador : AppCompatActivity() {

    private lateinit var binding : ActivityVisualizarEventoOrganizadorBinding
    private val graphicsEventDetailViewModel : GraphicsEventDetailViewModel by viewModels()
    private val getFunctionOrganizerViewModel : GetFunctionOrganizerViewModel by viewModels()
    val repository = Repository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_evento_organizador)

        binding = ActivityVisualizarEventoOrganizadorBinding.inflate(layoutInflater)
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


        val userUid = getSharedPreferences("userUid", Context.MODE_PRIVATE)
            .getString("userUid", "").toString()

        val idUser = "qVzK32OHDYOUtK1YsQbh"
        val idEvento = intent.getStringExtra("idEvento")
        val nombre = intent.getStringExtra("nombre")
        val descripcion = intent.getStringExtra("descripcion")
        val lugar = intent.getStringExtra("ubicacion")
        val direccion = intent.getStringExtra("direccion")
        val ciudad = intent.getStringExtra("ciudad")
        val estado = intent.getStringExtra("estado")
        val foto_portada = intent.getStringExtra("foto_portada")

        binding.NombreEvento.text = nombre
        binding.Ubicacion.text = lugar
        binding.DireccionVEE.text = direccion
        binding.CiudadEstadoVEE.text = ciudad + ", " + estado
        Picasso.get().load(foto_portada).into(binding.ImagenVEE)

        binding.btnMoreGraphics.setOnClickListener {
            var idEvent : String = idEvento.toString()

            val intent = Intent(this, DetailedMetrics::class.java)

            intent.putExtra("idEvent", idEvent)

            startActivity(intent)
        }

        binding.addFunBtn.setOnClickListener{
            var idEvent : String = idEvento.toString()

            val crearProyectoForm =  Intent(this, CreateFunction::class.java)

            crearProyectoForm.putExtra("idEvent", idEvent)

            this.startActivity(crearProyectoForm)
        }
        binding.addArtBtn.setOnClickListener{
            var idEvent : String = idEvento.toString()

            val crearProyectoForm =  Intent(this, AddArtist::class.java)

            crearProyectoForm.putExtra("idEvent", idEvent)

            this.startActivity(crearProyectoForm)
        }
        binding.addCatBtn.setOnClickListener{
            var idEvent : String = idEvento.toString()

            val crearProyectoForm =  Intent(this, ActivityAddCategoria::class.java)

            crearProyectoForm.putExtra("idEvent", idEvent)

            this.startActivity(crearProyectoForm)
        }
        binding.addTBBtn.setOnClickListener{
            var idEvent : String = idEvento.toString()

            val crearProyectoForm =  Intent(this, ActivityAddTB::class.java)

            crearProyectoForm.putExtra("idEvent", idEvent)

            this.startActivity(crearProyectoForm)
        }

        binding.btnComment.setOnClickListener{
            var idEvent : String = idEvento.toString()
            val intent = Intent(this, GetCommentsActivity::class.java)
            intent.putExtra("idEvent", idEvent)
            this.startActivity(intent)
        }


        initRecyclerView(getFunctionOrganizerViewModel, idEvento.toString(), repository)

        //Creación de usuario temporal
        val tempEventId : String = "Nbb94T1aTzqT4RiXfmWm"
        val repository = Repository(this)
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
        ourPieChart.data.setValueFormatter(DefaultValueFormatter(0))
        ourPieChart.invalidate()
        data.setValueTextColor(Color.DKGRAY)
        data.setValueTextSize(20f)
        ourPieChart.getLegend().setTextColor(Color.DKGRAY)
        ourPieChart.getDescription().setTextColor(Color.DKGRAY)
        ourPieChart.setEntryLabelColor(Color.DKGRAY)
        ourPieChart.description.isEnabled = false
        ourPieChart.setDrawEntryLabels(false)
    }

    fun populateRating(eid:String){
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
        //repository = Repository(this)
        //Llamada a la función y al observador para modificar elementos de Rating
        graphicsEventDetailViewModel.getExtEventRating(eid, repository)
        graphicsEventDetailViewModel.ratingExt.observe(this, Observer{
            ourRatingBar.rating = it[8]
            ourRatingValue.text = "${it[8]} de 5"
            ourRatingCount.text =  "en ${it[1].toInt()} calificaciones"
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
        })
    }
    private fun initRecyclerView(getFunctionOrganizerViewModel: GetFunctionOrganizerViewModel, eventId: String, repository: Repository){
        getFunctionOrganizerViewModel.getFunctionOrganizer(eventId, repository)

        // Log.d("LOG Activity",getOrganizerEventViewModel.getOrganizerEvent(userIdTemp, repository).toString())
        getFunctionOrganizerViewModel.funcion.observe(this, Observer {
            binding.funcionesOrganizadorRV.layoutManager = LinearLayoutManager(this) // Le da el layout que usará el RV.
            binding.funcionesOrganizadorRV.adapter = ActivityVisualizarEventoOrganizadorAdapter(it)
        })
    }

}