package com.example.appatemporal.framework.view

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.databinding.ActivityCrearEventoBinding
import com.example.appatemporal.domain.models.FunctionModel
import java.time.LocalDate
import java.util.*

class CrearEventoOrganizador : AppCompatActivity(){
    private lateinit var binding: com.example.appatemporal.databinding.ActivityCrearEventoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearEventoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        OnClickTime()

        binding.addArtistBtn.setOnClickListener {
            addNewArtFormView()
        }

        binding.addFunctionBtn.setOnClickListener {
            addNewFunFormView()
        }

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

    private fun addNewArtFormView() {
        val inflater = LayoutInflater.from(this).inflate(R.layout.item_artista_form, null)
        binding.artistaFormLayout.addView(inflater, binding.funcionFormLayout.childCount)
    }

    private fun addNewFunFormView() {
        val inflater = LayoutInflater.from(this).inflate(R.layout.item_crear_funcion, null)
        binding.funcionFormLayout.addView(inflater, binding.funcionFormLayout.childCount)
    }

}