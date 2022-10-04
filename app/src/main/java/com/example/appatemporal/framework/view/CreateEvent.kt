package com.example.appatemporal.framework.view


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.appatemporal.databinding.ActivityCrearEventoBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.EventModel
import com.example.appatemporal.domain.models.FunctionModel
import com.example.appatemporal.framework.viewModel.AddNewEventViewModel
import java.time.LocalTime
import java.util.*


class CreateEvent :AppCompatActivity() {
    private val viewModel: AddNewEventViewModel by viewModels()
    private lateinit var binding: ActivityCrearEventoBinding
    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearEventoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nombre = binding.NombreEvento
        val descripcion = binding.DescripcionEvento
        val ciudad = binding.CiudadEvento
        val estado = binding.EstadoEvento
        val ubicacion = binding.UbicacionEvento
        val direccion = binding.DireccionEvento
        val longitud = binding.LongitudEvento
        val latitud = binding.LatitudEvento
        val foto = binding.UrlImagenEvento
        val video = binding.URLVideoEvento
        val spinner = binding.TipoEvento
        val lista = listOf("Privado", "publico", "De paga", "gratis")
        val datePickerF = binding.datePicker1
        val horaInicio = binding.timePickerInicio
        val horaFin = binding.timePickerFin
        val artista= binding.ArtistaEvento
        val submit = binding.submitBtn


        horaInicio.setIs24HourView(true);
        horaFin.setIs24HourView(true);
        val today = Calendar.getInstance()
        datePickerF.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val month = month + 1
            val msg = "You Selected: $day/$month/$year"
        }

        binding.submitBtn.setOnClickListener {
            if((nombre.text.toString().isNotEmpty())&&(descripcion.text.toString().isNotEmpty())&&(ciudad.text.toString().isNotEmpty())&&(estado.text.toString().isNotEmpty())&&(ubicacion.text.toString().isNotEmpty())&&(direccion.text.toString().isNotEmpty())&&(longitud.text.toString().isNotEmpty())&&(latitud.text.toString().isNotEmpty())&&(foto.text.toString().isNotEmpty())&&(video.text.toString().isNotEmpty())){
                Log.d("El nombre del evento es ", "Kiubo" + nombre.text.toString())
                val activo = 1
                val divisa = "Pesos"
                val fecha_Creado = LocalTime.now().toString()
                val fecha_modificado = LocalTime.now().toString()
                val evento = EventModel(nombre.text.toString(), descripcion.text.toString(),ciudad.text.toString(),estado.text.toString(), ubicacion.text.toString(),direccion.text.toString(),longitud.text.toString(),latitud.text.toString(),foto.text.toString(),video.text.toString(),activo,divisa,fecha_Creado,fecha_modificado)
                Log.d("El último ticket es:", evento.ciudad)
                //val artista = findViewById<EditText>(R.id.Artista_Evento)
                val repository = Repository(this)

                val hourI = horaInicio.hour
                val minuteI = horaInicio.minute
                val hourF = horaFin.hour
                val minuteF = horaFin.minute

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
                val funcion=FunctionModel(fecha,hora_stringI,hora_stringF)
                viewModel.AddEvent(evento, repository, artista.text.toString(),funcion)
            }
            else{
                Toast.makeText(applicationContext, "Llena todos los campos antes de continuar.", Toast.LENGTH_SHORT).show()
            }

        }


    }
}