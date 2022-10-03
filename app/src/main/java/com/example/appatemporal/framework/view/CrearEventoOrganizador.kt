package com.example.appatemporal.framework.view

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.appatemporal.R
import com.example.appatemporal.databinding.ActivityCrearEventoBinding

class CrearEventoOrganizador : AppCompatActivity(){
    private lateinit var binding: com.example.appatemporal.databinding.ActivityCrearEventoBinding
    private var item_artista = R.layout.item_artista_form

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearEventoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        OnClickTime()

        removeArtFormView(R.layout.item_artista_form)

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

    private fun removeArtFormView(view: Int) {

    }

    private fun addNewFunFormView() {
        val inflater = LayoutInflater.from(this).inflate(R.layout.item_crear_funcion, null)
        binding.funcionFormLayout.addView(inflater, binding.funcionFormLayout.childCount)
    }

    private fun removeFunFormView() {

    }

}