package com.example.appatemporal.framework.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.databinding.ActivityCrearEventoBinding
import com.example.appatemporal.domain.models.FunctionModel
import java.time.LocalDate
import java.util.*

class CrearEventoOrganizador : AppCompatActivity(){
    private lateinit var binding: ActivityCrearEventoBinding
    var funcionFirst: FunctionModel = FunctionModel(Date(2022, 4,14),"23:00",  "03:00")

    val funcionList = mutableListOf<FunctionModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearEventoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBtn.setOnClickListener {
            addNewView()
        }

    }

    private fun addNewView() {
        val inflater = LayoutInflater.from(this).inflate(R.layout.item_crear_funcion, null)
        binding.funcionFormLayout.addView(inflater, binding.funcionFormLayout.childCount)
    }

}