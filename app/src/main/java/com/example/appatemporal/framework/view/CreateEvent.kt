package com.example.appatemporal.framework.view


import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.appatemporal.R
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.EventModel
import com.example.appatemporal.framework.viewModel.AddNewEventViewModel
import java.time.LocalTime



class CreateEvent :AppCompatActivity() {
    private val viewModel: AddNewEventViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_evento)

        val spinner = findViewById<Spinner>(R.id.TipoEvento)
        val lista = listOf("Privado", "publico", "De paga", "gratis")

        val submit = findViewById<Button>(R.id.submitBtn)
        submit.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.Nombre_Evento)
            val descripcion = findViewById<EditText>(R.id.Descripcion_Evento)
            val ciudad = findViewById<EditText>(R.id.Ciudad_Evento)
            val estado = findViewById<EditText>(R.id.Estado_Evento)
            val ubicacion = findViewById<EditText>(R.id.Ubicacion_Evento)
            val direccion = findViewById<EditText>(R.id.Direccion_Evento)
            val longitud = findViewById<EditText>(R.id.Longitud_Evento)
            val latitud = findViewById<EditText>(R.id.Latitud_Evento)
            val foto = findViewById<EditText>(R.id.UrlImagenEvento)
            val video = findViewById<EditText>(R.id.URL_Video_Evento)
            val activo = 1
            val divisa = "pesos"
            val fecha_Creado = LocalTime.now().toString()
            val fecha_modificado = LocalTime.now().toString()
            val evento = EventModel(nombre.text.toString(), descripcion.text.toString(),ciudad.text.toString(),estado.text.toString(), ubicacion.text.toString(),direccion.text.toString(),longitud.text.toString(),latitud.text.toString(),foto.text.toString(),video.text.toString(),activo,divisa,fecha_Creado,fecha_modificado)
            //val artista = findViewById<EditText>(R.id.Artista_Evento)

            val repository = Repository(this)
            viewModel.AddEvent(evento, repository)


        }
    }
}