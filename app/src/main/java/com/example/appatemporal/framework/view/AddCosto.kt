package com.example.appatemporal.framework.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appatemporal.databinding.ActivityProyectoOrganizadorBinding

class AddCosto : AppCompatActivity() {
    private lateinit var binding: ActivityProyectoOrganizadorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProyectoOrganizadorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var myExtras : Bundle? = intent.extras

        binding.projectName.text = myExtras?.getString("nombre_costo")
    }

}