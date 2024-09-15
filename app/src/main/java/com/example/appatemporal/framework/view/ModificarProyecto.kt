package com.example.appatemporal.framework.view

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.appatemporal.R
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.appatemporal.data.localdatabase.entities.Estatus
import com.example.appatemporal.data.localdatabase.entities.Proyecto
import com.example.appatemporal.databinding.CreateNewProjectBinding
import com.example.appatemporal.databinding.ModifyNewProjectBinding
import com.example.appatemporal.databinding.VistaModificarproyectoBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.AddNewProjectViewModel
import com.example.appatemporal.framework.viewModel.ProyectoOrganizadorViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ModificarProyecto : AppCompatActivity(),View.OnClickListener {

    private val viewModel: ProyectoOrganizadorViewModel by viewModels()
    private lateinit var binding : ModifyNewProjectBinding
    lateinit var myCalendar: Calendar
    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    var finalDate = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ModifyNewProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.dateEdt.setOnClickListener(this)
        val repository = Repository(this)
        var myExtras :Bundle? = intent.extras
        binding.nameModifyProject.setText(myExtras?.getString("nombre_proyecto"))
        binding.dateEdt.setText(myExtras?.getString("fecha_inicio"))
        val idproject: Int = myExtras?.getInt("id_proyecto")?:-1
        // Set click listener
        binding.modifybutton.setOnClickListener {
            // Get values from view
            val name = binding.nameModifyProject.text.toString()
            val date = binding.dateEdt.text.toString()
            val tsLong = System.currentTimeMillis() / 1000
            val ts: String = tsLong.toString()
            if (name.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Faltan campos por llenar", Toast.LENGTH_SHORT).show()
            }
            else if (name.isEmpty()){
                Toast.makeText(this, "No se especificó el nombre", Toast.LENGTH_SHORT).show()
            }
            else if (date.isEmpty()){
                Toast.makeText(this, "No se especificó la fecha", Toast.LENGTH_SHORT).show()
            }
            else {
                viewModel.updateModify(name,date,ts,idproject, repository)

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
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
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