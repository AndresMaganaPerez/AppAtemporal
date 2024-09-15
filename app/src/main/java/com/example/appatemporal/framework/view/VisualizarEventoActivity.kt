package com.example.appatemporal.framework.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appatemporal.databinding.ActivityVisualizareventoBinding

class VisualizarEventoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVisualizareventoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVisualizareventoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}