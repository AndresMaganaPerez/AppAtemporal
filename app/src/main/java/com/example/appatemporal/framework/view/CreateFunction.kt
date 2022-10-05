package com.example.appatemporal.framework.view


import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi

import androidx.appcompat.app.AppCompatActivity
import com.example.appatemporal.R
import com.example.appatemporal.databinding.ActivityAddArtistaBinding
import com.example.appatemporal.databinding.ActivityCreateFunctionBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.AddNewEventViewModel
import kotlinx.android.synthetic.main.activity_create_function.*
import java.util.*

class CreateFunction : AppCompatActivity() {

    private lateinit var binding: ActivityCreateFunctionBinding
    private val viewModel: AddNewEventViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateFunctionBinding.inflate(layoutInflater)
        val datePickerF = binding.datePicker1
        val horaInicio = binding.timePickerInicio
        val horaFin = binding.timePickerFin
        val btn = binding.submitBtn
        setContentView(binding.root)
        val myExtras : Bundle? = intent.extras

        horaInicio.setIs24HourView(true);
        horaFin.setIs24HourView(true);

        val today = Calendar.getInstance()
        datePickerF.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val month = month + 1
            val msg = "You Selected: $day/$month/$year"
            Toast.makeText(this@CreateFunction, msg, Toast.LENGTH_SHORT).show()
        }

        btn.setOnClickListener{
            val hourI = horaInicio.hour
            val minuteI = horaInicio.minute
            val hourF = horaFin.hour
            val minuteF = horaFin.minute
            val repository = Repository(this)
            val hoursI = if (hourI < 10) "0" + hourI else hourI
            val minI = if (minuteI < 10) "0" + minuteI else minuteI
            val hora_stringI="$hoursI:$minI"

            val hoursF = if (hourF < 10) "0" + hourF else hourF
            val minF = if (minuteF < 10) "0" + minuteF else minuteF
            val hora_stringF="$hoursF:$minF"

            val year = datePickerF.year
            var month = datePickerF.month
            val day = datePickerF.dayOfMonth
            month = month + 1
            val fecha="$day/$month/$year"
            val eid:String="41RrBlaCdfqkMTDmMcT7"
            viewModel.AddFunction(eid,repository,fecha,hora_stringI,hora_stringF)
        }

    }
}