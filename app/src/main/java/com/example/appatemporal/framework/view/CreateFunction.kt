package com.example.appatemporal.framework.view


import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import com.example.appatemporal.R
import com.example.appatemporal.databinding.ActivityCreateFunctionBinding
import com.example.appatemporal.framework.viewModel.AddNewEventViewModel
import kotlinx.android.synthetic.main.activity_create_function.*
import java.util.*

class CreateFunction : AppCompatActivity() {

    private fun OnClickTime() {
        val timePickerI=binding.timePickerInicio
        val timePickerF=binding.timePickerFin
        timePickerI.setIs24HourView(true);
        timePickerF.setIs24HourView(true);

        timePickerI.setOnTimeChangedListener { _, hour, minute -> var hour = hour
            val hoursI = if (hour < 10) "0" + hour else hour
            val minI = if (minute < 10) "0" + minute else minute
            val hora_stringI="$hoursI:$minI"
        }
        timePickerF.setOnTimeChangedListener { _, hour, minute -> var hour = hour
            val hoursF = if (hour < 10) "0" + hour else hour
            val minF = if (minute < 10) "0" + minute else minute
            val hora_stringF="$hoursF:$minF"
        }
    }

    private lateinit var binding: ActivityCreateFunctionBinding
    private val viewModel: AddNewEventViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val datePicker = binding.datePicker1
        val horaInicio = binding.timePickerInicio
        val horaFin = binding.timePickerFin
        val btn = binding.submitBtn
        setContentView(R.layout.activity_create_function)
        OnClickTime()
        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month + 1
            val msg = "You Selected: $day/$month/$year"
            Toast.makeText(this@CreateFunction, msg, Toast.LENGTH_SHORT).show()
        }

        btn.setOnClickListener{
            val hourI = horaInicio.hour
            val minuteI = horaInicio.minute

            val hoursI = if (hourI < 10) "0" + hourI else hourI
            val minI = if (minuteI < 10) "0" + minuteI else minuteI
            val hora_stringI="$hoursI:$minI"




        }

    }
}