package com.example.appatemporal.framework.view


import android.os.Bundle
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.appatemporal.R

class CreateEvent :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_evento)

       val spinner = findViewById<Spinner>(R.id.TipoEvento)
        val lista = listOf("Privado","publico","De paga", "gratis")
    }
}