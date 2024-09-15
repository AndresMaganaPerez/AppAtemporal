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
import com.example.appatemporal.data.localdatabase.entities.Actividad
import com.example.appatemporal.databinding.ActivityTaskBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.AddNewActivityViewModel
import kotlinx.coroutines.launch

class AddNewActivityForm : AppCompatActivity(){
    private val viewModel: AddNewActivityViewModel by viewModels()
    private lateinit var binding : ActivityTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

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


        // get reference to the string array that we just created
        val areaList = resources.getStringArray(R.array.areaList)
        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        val arrayAdapter1 = ArrayAdapter(this, R.layout.dropdown_menu, areaList)
        // get reference to the autocomplete text view
        val autocompleteTV1 = findViewById<AutoCompleteTextView>(R.id.spinnerArea)
        // set adapter to the autocomplete tv to the arrayAdapter
        autocompleteTV1.setAdapter(arrayAdapter1)

        val estatusList = resources.getStringArray(R.array.estatusList)
        val arrayAdapter2 = ArrayAdapter(this, R.layout.dropdown_menu, estatusList)
        val autocompleteTV2 = findViewById<AutoCompleteTextView>(R.id.spinnerEstatus)
        autocompleteTV2.setAdapter(arrayAdapter2)

        val prioridadList = resources.getStringArray(R.array.prioridadList)
        val arrayAdapter3 = ArrayAdapter(this, R.layout.dropdown_menu, prioridadList)
        val autocompleteTV3 = findViewById<AutoCompleteTextView>(R.id.spinnerPrioridad)
        autocompleteTV3.setAdapter(arrayAdapter3)

        var myExtras :Bundle? = intent.extras
        val idproject: Int = myExtras?.getInt("id_proyecto")?:-1

        val repository = Repository(this)

        // Set click listener
        binding.saveBtn.setOnClickListener {
            // Get values from view
            val name = binding.nameActivity.text.toString()
            val area = binding.spinnerArea.text.toString()
            val estatus = binding.spinnerEstatus.text.toString()
            val prioridad = binding.spinnerPrioridad.text.toString()
            if (name.isBlank() || area.isBlank() || estatus.isBlank() || prioridad.isBlank()){
                Toast.makeText(this, "Faltan campos por completar", Toast.LENGTH_SHORT).show()
            }
            else {
                val actividad: Actividad = Actividad(0, 1,name, area, estatus, prioridad, idproject)

                viewModel.addNewActividad(actividad, repository)

                // Go back to main activity
                val intent = Intent(this, DeleteActivity::class.java)
                with(intent){
                    putExtra("id_proyecto", idproject)
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
            finish()
        }

    }
}