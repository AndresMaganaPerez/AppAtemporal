package com.example.appatemporal.framework.view

import android.os.Bundle
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.appatemporal.R

class ActivityCrearFuncion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_funcion)
        OnClickTime()
    }

    private fun OnClickTime() {
        val timePickerI = findViewById<TimePicker>(R.id.timePickerInicio)
        val timePickerF = findViewById<TimePicker>(R.id.timePickerFin)
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
}