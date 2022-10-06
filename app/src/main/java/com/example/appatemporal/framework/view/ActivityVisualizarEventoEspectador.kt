package com.example.appatemporal.framework.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appatemporal.databinding.ActivityVisualizareventoBinding
import com.example.appatemporal.domain.Repository
import com.squareup.picasso.Picasso

class ActivityVisualizarEventoEspectador : AppCompatActivity() {
    private lateinit var binding: ActivityVisualizareventoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val repository = Repository(this)
        super.onCreate(savedInstanceState)
        binding = ActivityVisualizareventoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Implementación de la NavBar
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

        val idEvent = intent.getStringExtra("idEvent")
        val nombre = intent.getStringExtra("nombre")
        val direccion = intent.getStringExtra("direccion")
        val estado = intent.getStringExtra("estado")
        val ubicacion = intent.getStringExtra("ubicacion")
        val foto_portada = intent.getStringExtra("foto_portada")

        binding.NombreEvento.text = nombre
        binding.DireccionVEE.text = direccion
        binding.Ubicacion.text = ubicacion
        binding.CiudadEstadoVEE.text = estado

        Picasso.get().load(foto_portada).into(binding.ImagenVEE)

        binding.buttonComprar.setOnClickListener{
            val url = "http://www.atemporal.art"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
    }


}