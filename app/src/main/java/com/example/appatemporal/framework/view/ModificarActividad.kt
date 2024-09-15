package com.example.appatemporal.framework.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.appatemporal.R
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.DeleteActivityViewModel
import com.example.appatemporal.data.localdatabase.entities.Actividad
import com.example.appatemporal.databinding.ModifyNewActivityBinding
import kotlinx.coroutines.launch

class ModificarActividad : AppCompatActivity(){
    private val viewModel: DeleteActivityViewModel by viewModels()
    private lateinit var binding : ModifyNewActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ModifyNewActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository(this)
        var myExtras :Bundle? = intent.extras
        binding.nameModifedActivity.setText(myExtras?.getString("nombre_actividad"))
        val idactividad: Int = myExtras?.getInt("id_actividad")?:-1
        val idproyecto: Int = myExtras?.getInt("id_proyecto")?:-1

        val areaList = resources.getStringArray(R.array.areaList)
        val arrayAdapter1 = ArrayAdapter(this, R.layout.dropdown_menu, areaList)
        val autocompleteTV1 = findViewById<AutoCompleteTextView>(R.id.spinnerModifiedArea)
        autocompleteTV1.setAdapter(arrayAdapter1)

        val estatusList = resources.getStringArray(R.array.estatusList)
        val arrayAdapter2 = ArrayAdapter(this, R.layout.dropdown_menu, estatusList)
        val autocompleteTV2 = findViewById<AutoCompleteTextView>(R.id.spinnerModifiedEstatus)
        autocompleteTV2.setAdapter(arrayAdapter2)

        val prioridadList = resources.getStringArray(R.array.prioridadList)
        val arrayAdapter3 = ArrayAdapter(this, R.layout.dropdown_menu, prioridadList)
        val autocompleteTV3 = findViewById<AutoCompleteTextView>(R.id.spinnerModifiedPrioridad)
        autocompleteTV3.setAdapter(arrayAdapter3)

        // Set click listener
        binding.saveModifiedBtn.setOnClickListener {
            Log.d("idPrueba", idproyecto.toString())
            Log.d("idActividad", idactividad.toString())

            // Get values from view
            val name = binding.nameModifedActivity.text.toString()
            val area = binding.spinnerModifiedArea.text.toString()
            val estatus = binding.spinnerModifiedEstatus.text.toString()
            val prioridad = binding.spinnerModifiedPrioridad.text.toString()
            if (name.isBlank() || area.isBlank() || estatus.isBlank() || prioridad.isBlank()){
                Toast.makeText(this, "Faltan campos por completar", Toast.LENGTH_SHORT).show()
            }
            else {
                val actividad: Actividad = Actividad( idactividad, 0,name, area, estatus, prioridad, idproyecto)
                viewModel.updateActividad(idproyecto,name,estatus,area,prioridad, idactividad, repository)
                // Go back to main activity
                val intent = Intent(this, DeleteActivity::class.java)
                intent.putExtra("id_proyecto", idproyecto)
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
            finish()
        }

    }
}