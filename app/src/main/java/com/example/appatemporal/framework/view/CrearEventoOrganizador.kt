package com.example.appatemporal.framework.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import androidx.databinding.DataBindingUtil.setContentView
import com.example.appatemporal.R
import com.example.appatemporal.databinding.ActivityCrearEventoBinding

class CrearEventoOrganizador : AppCompatActivity(){
    private lateinit var binding: ActivityCrearEventoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearEventoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.agregarFuncionBtn.setOnClickListener{
            addNewFunctionForm()
        }
    }

    private fun addNewFunctionForm() {
        val inflater = LayoutInflater.from(this).inflate(R.layout.activity_crear_funcion, null)
        binding.layoutForm.addView(inflater, binding.layoutForm.childCount)
    }

}