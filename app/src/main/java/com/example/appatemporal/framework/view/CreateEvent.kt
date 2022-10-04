package com.example.appatemporal.framework.view


import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.appatemporal.R
import com.example.appatemporal.data.constants.Constantes.Companion.descripcion
import com.example.appatemporal.data.constants.Constantes.Companion.direccion
import com.example.appatemporal.data.constants.Constantes.Companion.estado
import com.example.appatemporal.databinding.ActivityCrearEventoBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.domain.models.EventModel
import com.example.appatemporal.framework.viewModel.AddNewEventViewModel
import java.time.LocalTime
import java.util.*


class CreateEvent :AppCompatActivity() {
    private val viewModel: AddNewEventViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    private lateinit var binding: ActivityCrearEventoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        setContentView(R.layout.activity_crear_evento)

        val spinner = binding.TipoEvento
        val lista = listOf("Privado", "publico", "De paga", "gratis")
        val datePickerF = binding.datePicker1
        val horaInicio = binding.timePickerInicio
        val horaFin = binding.timePickerFin
        val submit = binding.submitBtn
        horaInicio.setIs24HourView(true);
        horaFin.setIs24HourView(true);

        val today = Calendar.getInstance()
        datePickerF.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val month = month + 1
            val msg = "You Selected: $day/$month/$year"
            Toast.makeText(this@CreateEvent, msg, Toast.LENGTH_SHORT).show()
        }

        submit.setOnClickListener {
            val activo = 1
            val divisa = "Pesos"
            val fecha_Creado = LocalTime.now().toString()
            val fecha_modificado = LocalTime.now().toString()
            val evento = EventModel(nombre.text.toString(), descripcion.text.toString(),ciudad.text.toString(),estado.text.toString(), ubicacion.text.toString(),direccion.text.toString(),longitud.text.toString(),latitud.text.toString(),foto.text.toString(),video.text.toString(),activo,divisa,fecha_Creado,fecha_modificado)
            //val artista = findViewById<EditText>(R.id.Artista_Evento)
            val repository = Repository(this)
            viewModel.AddEvent(evento, repository)

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
        }
    }
}