package com.example.appatemporal.framework.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appatemporal.databinding.ActivityMainHomepageOrganizadorBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.view.adapters.ActivityMainHomepageOrganizadorAdapterHorizontal
import com.example.appatemporal.framework.view.adapters.ActivityMainHomepageOrganizadorAdapterVertical
import com.example.appatemporal.framework.viewModel.GetEventsUserOrg

class ActivityMainHomepageOrganizador : AppCompatActivity() {
    private lateinit var binding: ActivityMainHomepageOrganizadorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val getEventsUserOrg : GetEventsUserOrg by viewModels()
        val repository = Repository(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainHomepageOrganizadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userIdTemp = "qVzK32OHDYOUtK1YsQbh"

        initRecyclerViewHorizontal(getEventsUserOrg, userIdTemp, repository)
        initRecyclerViewVertical(getEventsUserOrg, repository)

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

    private fun initRecyclerViewHorizontal(getEventsUserOrg: GetEventsUserOrg, userIdTemp: String, repository: Repository){
        getEventsUserOrg.getEventsOrg(userIdTemp, repository)
        // Log.d("LOG Activity",getEventsUserOrg.getEventsOrg(userIdTemp, repository).toString())
        getEventsUserOrg.allEventsOrg.observe(this, Observer { eventList ->
            val linearLayout = LinearLayoutManager(this)
            linearLayout.orientation=LinearLayoutManager.HORIZONTAL
            binding.HorizontalOrgView.layoutManager = linearLayout // Le da el layout que usará el RV.
            binding.HorizontalOrgView.adapter = ActivityMainHomepageOrganizadorAdapterHorizontal(eventList)
        })
    }

    private fun initRecyclerViewVertical(getEventsUserOrg: GetEventsUserOrg, repository: Repository){
        getEventsUserOrg.getAllEvents(repository)
        // Log.d("LOG Activity",getEventsInMonthViewModel.getAllEvents(repository).toString())
        getEventsUserOrg.allEvents.observe(this, Observer { eventList ->
            val linearLayout = LinearLayoutManager(this)
            linearLayout.orientation=LinearLayoutManager.VERTICAL
            binding.VerticalOrgView.layoutManager = linearLayout // Le da el layout que usará el RV.
            binding.VerticalOrgView.adapter = ActivityMainHomepageOrganizadorAdapterVertical(eventList)
        })
    }

}
