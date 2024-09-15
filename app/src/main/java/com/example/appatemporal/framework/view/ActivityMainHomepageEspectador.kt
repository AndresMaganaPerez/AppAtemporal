package com.example.appatemporal.framework.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appatemporal.databinding.ActivityMainHomepageEspectadorBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.view.adapters.ActivityMainHomepageEspectadorAdapterHorizontal
import com.example.appatemporal.framework.view.adapters.ActivityMainHomepageEspectadorAdapterVertical
import com.example.appatemporal.framework.viewModel.GetEventsInMonthViewModel
import java.util.*

class ActivityMainHomepageEspectador : AppCompatActivity() {
    private lateinit var binding: ActivityMainHomepageEspectadorBinding
    val getEventsInMonthViewModel : GetEventsInMonthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainHomepageEspectadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository(this)

        val userIdTemp = "pod6xLDUeRNZItm7u93DC5CYbgJ2"
        val dia = getDay()
        val month = getMonth()
        val year = getYear()

        initRecyclerViewHorizontal(getEventsInMonthViewModel, dia, month, year ,repository)
        initRecyclerViewVertical(getEventsInMonthViewModel, repository)

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

    fun getDay() : Int {
        val cal = Calendar.getInstance()
        val dayOfMonth =  cal[Calendar.DAY_OF_MONTH]
        // Log.d("pruebasDia-Actual",dayOfMonth.toString())
        return dayOfMonth
    }

    fun getMonth() : Int {
        val cal = Calendar.getInstance()
        val numberOfMonth =  cal[Calendar.MONTH]+1
        // Log.d("pruebasMes-Actual",numberOfMonth.toString())
        return numberOfMonth
    }

    fun getYear() : Int {
        val cal = Calendar.getInstance()
        val year =  cal[Calendar.YEAR]
        //Log.d("pruebasAño-Actual",year.toString())
        return year
    }

    private fun initRecyclerViewHorizontal(getEventsInMonthViewModel: GetEventsInMonthViewModel, day: Int, month: Int, year: Int, repository: Repository){
        getEventsInMonthViewModel.getEventsMonth(day, month, year, repository)
        //Log.d("LOG Activity",getEventsInMonthViewModel.getEventsMonth(day, month, year, repository).toString())
        getEventsInMonthViewModel.eventsMonth.observe(this, Observer { eventsList ->
            val linearLayout = LinearLayoutManager(this)
            linearLayout.orientation=LinearLayoutManager.HORIZONTAL
            binding.HorizontalEspectadorView.layoutManager = linearLayout // Le da el layout que usará el RV.
            binding.HorizontalEspectadorView.adapter = ActivityMainHomepageEspectadorAdapterHorizontal(eventsList)
        })
    }

    private fun initRecyclerViewVertical(getEventsInMonthViewModel: GetEventsInMonthViewModel, repository: Repository){
        getEventsInMonthViewModel.getAllEvents(repository)
        //Log.d("LOG Activity",getEventsInMonthViewModel.getEventsMonth(day, month, year, repository).toString())
        getEventsInMonthViewModel.allEvents.observe(this, Observer { eventsList ->
            val linearLayout = LinearLayoutManager(this)
            linearLayout.orientation=LinearLayoutManager.VERTICAL
            binding.VerticalView.layoutManager = linearLayout // Le da el layout que usará el RV.
            binding.VerticalView.adapter = ActivityMainHomepageEspectadorAdapterVertical(eventsList)
        })
    }
}