package com.example.lab

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R

class activity_actividadesorganizador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividades_organizador)
        initRecyclerView()
    }
    @SuppressLint("WrongViewCast")
    private fun initRecyclerView(){
        val cardbuttonsAO: List<Card_button> = listOf(
            Card_button("Nueva Tarea"),
            Card_button("Comprar Flores"),
            Card_button("Comprar Vestuario"),
            Card_button("Pago de la renta del lugar")
        )
        val recyclerViewVerticalA0  = findViewById<RecyclerView>(R.id.A0_RV)
        val linearLayoutManager = LinearLayoutManager(this )
        linearLayoutManager.orientation=LinearLayoutManager.VERTICAL
        recyclerViewVerticalA0.layoutManager = linearLayoutManager
        recyclerViewVerticalA0.adapter = AdapterAO(cardbuttonsAO)
    }
}