package com.example.appatemporal.framework.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.databinding.ActivityCrearEventoBinding
import com.example.appatemporal.databinding.ItemArtistaFormBinding
import com.example.appatemporal.databinding.ItemCrearFuncionBinding
import com.example.appatemporal.domain.models.FunctionModel
import kotlinx.android.synthetic.main.item_artista_form.view.*
import java.time.LocalDate
import java.util.*
import kotlin.reflect.typeOf

class CrearEventoOrganizador : AppCompatActivity(){
    private lateinit var binding: ActivityCrearEventoBinding
    private lateinit var bindingIArt: ItemArtistaFormBinding
    private lateinit var bindingIFun: ItemCrearFuncionBinding

    val artItems = mutableMapOf<Int, View>()

    // var artItems : Array<View?> = arrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityCrearEventoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Time input
        OnClickTime()

        // Agregar Formulario de Artista
        binding.addArtistBtn.setOnClickListener {
            Log.d("Add Btn Click: ", "Se presionó el botón agregar artista")
            addNewArtFormView()
        }

        // Eliminar Formulario de Artista
        removeArtFormView()

        // Agregar Formulario de Función
        binding.addFunctionBtn.setOnClickListener {
            addNewFunFormView()
        }

        // Eliminar Formulario de Función
        removeFunFormView()

        // Submit Button
        binding.submitBtn.setOnClickListener {
            saveData()
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
        binding.artistaFormLayout.addView(inflater, binding.artistaFormLayout.childCount)

        artItems.put(binding.artistaFormLayout.childCount, inflater)
        println(artItems)

    }

    private fun removeArtFormView() {
        // Eliminar Formulario de Artista
        val count = binding.artistaFormLayout.childCount
        var item : View?

        Log.d("Prueba: ", "Test01")

        for (i in 0 until count) {
            item = binding.artistaFormLayout.getChildAt(i)
            //artItems.plus(item)


            item.deleteArtBtn.setOnClickListener {
                Log.d("Delete Btn Click: ", "Se presionó el botón delete artista")
                binding.artistaFormLayout.removeView(item)
            }
        }
        Log.d("Arreglo de items de Artist Form: ", artItems.toString())
    }

    private fun addNewFunFormView() {
        val inflater = LayoutInflater.from(this).inflate(R.layout.item_crear_funcion, null)
        binding.funcionFormLayout.addView(inflater, binding.funcionFormLayout.childCount)
    }

    private fun removeFunFormView() {
        /*// Eliminar Formulario de Función
        val count2 = binding.funcionFormLayout.childCount
        var item2 : View?
        var funItems : Array<View?> = arrayOf()

        Log.d("Prueba: ", "Test02")

        for (j in 0 until count2) {
            item2 = binding.funcionFormLayout.getChildAt(j)
            funItems.plus(item2)

            item2.deleteFunBtn.setOnClickListener {
                Log.d("Delete Btn Click: ", "Se presionó el botón delete artista")
                binding.funcionFormLayout.removeView(item2)
            }
        }*/
    }

    fun saveData() {
        val nombreEvento = binding.NombreEvento
        val descripcion = binding.DescripcionEvento
        val direccion = binding.DireccionEvento
        val ubicacion = binding.UbicacionEvento
        val ciudad = binding.CiudadEvento
        val estado = binding.EstadoEvento
        val latitud = binding.LatitudEvento
        val longitud = binding.LongitudEvento
        val imagen = binding.UrlImagenEvento
        val video = binding.URLVideoEvento
    }

}