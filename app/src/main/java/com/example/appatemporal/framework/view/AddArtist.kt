package com.example.appatemporal.framework.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.appatemporal.databinding.ActivityAddArtistaBinding
import com.example.appatemporal.databinding.ActivityCrearEventoBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.AddNewEventViewModel
/**
 * This file is linked with activity_add_artista.xml
 * This file is in charge of controlling the logic behind the funtion add an artist
 *
 * @see activity_add_artista.xml
 *
 * @author Resendiz & Camalich
 *
 * */
class AddArtist: AppCompatActivity() {
    private lateinit var binding: ActivityAddArtistaBinding
    private val viewModel: AddNewEventViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddArtistaBinding.inflate(layoutInflater)
        val nombreArtista = binding.nuevoArtista
        val btn = binding.btnArtista
        setContentView(binding.root)

        val idEvent = intent.getStringExtra("idEvent")

        btn.setOnClickListener{
            if(nombreArtista.text.toString().isNotEmpty()){
                val repository = Repository(this)
                val eid : String = idEvent.toString()
                viewModel.AddArtista(eid,repository, nombreArtista.text.toString())
                val submitBtn =  Intent(this, ActivityMisEventosOrganizador::class.java)
                this.startActivity(submitBtn)
            }
            else{
                Toast.makeText(applicationContext, "Llena el campo antes de continuar.", Toast.LENGTH_SHORT).show()
            }

        }
    }
}