package com.example.appatemporal.framework.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.adapters.ViewGroupBindingAdapter.setListener
import androidx.lifecycle.lifecycleScope
import com.example.appatemporal.R
import com.example.appatemporal.data.localdatabase.entities.Objetivo
import com.example.appatemporal.data.localdatabase.entities.Proyecto
import com.example.appatemporal.databinding.CreateNewProjectBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.AddNewProjectViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddNewProjectForm : AppCompatActivity(), View.OnClickListener {
    private val viewModel: AddNewProjectViewModel by viewModels()
    private lateinit var binding : CreateNewProjectBinding
    lateinit var myCalendar: Calendar
    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    var finalDate = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CreateNewProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.dateEdt.setOnClickListener(this)
        val repository = Repository(this)

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



        // Set click listener
        binding.button.setOnClickListener {
            // Get values from view
            val name = binding.nameCreateNewProject.text.toString()
            val date = binding.dateEdt.text.toString()
            val tsLong = System.currentTimeMillis() / 1000
            val ts: String = tsLong.toString()
            if (name.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Faltan campos por rellenar", Toast.LENGTH_SHORT).show()
            }
            else if (name.isEmpty()){
                Toast.makeText(this, "No se especificó el nombre", Toast.LENGTH_SHORT).show()
            }
            else if (date.isEmpty()){
                Toast.makeText(this, "No se especificó la fecha", Toast.LENGTH_SHORT).show()
            }
            else {
                val project: Proyecto = Proyecto(0, 1, name, date,0.0,0.0, 0.0,false, ts)

                lifecycleScope.launch{
                    viewModel.addNewProject(project, repository)
                }

                // Go back to main activity
                val intent = Intent(this, ProyectoOrganizador::class.java)
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
    override fun onClick(v: View) {
        when (v.id) {
            R.id.dateEdt -> {
                setListener()
            }
        }

    }

    private fun setListener() {
        myCalendar = Calendar.getInstance()

        dateSetListener =
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDate()

            }

        val datePickerDialog = DatePickerDialog(
            this, dateSetListener, myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun updateDate() {
        //01/03/2022
        val myformat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myformat)
        finalDate = myCalendar.time.time
        binding.dateEdt.setText(sdf.format(myCalendar.time))

        //timeInptLay.visibility = View.VISIBLE

    }

}