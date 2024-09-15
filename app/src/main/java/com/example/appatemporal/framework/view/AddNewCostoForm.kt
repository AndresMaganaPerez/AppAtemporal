package com.example.appatemporal.framework.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.appatemporal.data.localdatabase.entities.Costo
import com.example.appatemporal.databinding.CostoTaskBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.AddNewCostoViewModel
import kotlinx.coroutines.launch

class AddNewCostoForm : AppCompatActivity(){
    private val viewModel: AddNewCostoViewModel by viewModels()
    private lateinit var binding : CostoTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CostoTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var myExtras :Bundle? = intent.extras
        var idProyecto: Int=  myExtras?.getInt("id_proyecto")?:-1
        val repository = Repository(this)

        // Set click listener
        binding.savecostoBtn.setOnClickListener {
            // Get values from view
            val name = binding.nameCosto.text.toString()
            val amount = binding.montoCosto.text.toString()
            if (name.isEmpty() && amount.isEmpty()) {
                Toast.makeText(this, "No se especificó ningún dato", Toast.LENGTH_SHORT).show()
            }
            else if (name.isEmpty()) {
                Toast.makeText(this, "No se especificó nombre", Toast.LENGTH_SHORT).show()
            }
            else if (amount.isEmpty()) {
                Toast.makeText(this, "No se especificó el costo", Toast.LENGTH_SHORT).show()
            }
            else {
                val amount = binding.montoCosto.text.toString().toInt()
                val costo: Costo = Costo(0, name, amount, idProyecto)

                lifecycleScope.launch {
                    viewModel.addNewCosto(costo, repository)
                }
                // Go back to main activity
                val intent = Intent(this, DeleteCosto::class.java)
                with(intent) {
                    putExtra("id_proyecto", idProyecto)
                }
                startActivity(intent)
            }
        }

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
}