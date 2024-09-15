package com.example.appatemporal.framework.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appatemporal.databinding.ActivitySoporteBinding

class SupportActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySoporteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoporteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFAQ3.setOnClickListener{
            val userRol = getSharedPreferences("user", Context.MODE_PRIVATE)
                .getString("rol", "").toString()
            if(userRol == "Espectador"){
                val intent = Intent(this,FaqEspectador::class.java)
                startActivity(intent)
            } else if(userRol == "Organizador") {
                val intent = Intent(this, FaqOrganizador::class.java)
                startActivity(intent)
            }else if(userRol =="Ayudante"){
                val intent = Intent(this, FaqAyudante::class.java)
                startActivity(intent)
            }
        }

        binding.btnContacto3.setOnClickListener {
            val intent = Intent(this, ContactInfo::class.java)
            startActivity(intent)
        }

        binding.btnTerminos3.setOnClickListener {
            val intent = Intent(this, TermsCond::class.java)
            startActivity(intent)
        }

        binding.btnFallas3.setOnClickListener {
            val intent = Intent(this, ReportFailureActivity::class.java)
            startActivity(intent)
        }
    }
}