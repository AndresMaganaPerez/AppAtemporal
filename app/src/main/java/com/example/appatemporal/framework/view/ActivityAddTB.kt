package com.example.appatemporal.framework.view

import android.R
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.appatemporal.databinding.ActivityAddCategoriaBinding
import com.example.appatemporal.databinding.ActivityAddTipoBoletoBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.GetEventTBFilterViewModel

class ActivityAddTB : AppCompatActivity() {
    private val viewModel: GetEventTBFilterViewModel by viewModels()
    private lateinit var binding: ActivityAddTipoBoletoBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTipoBoletoBinding.inflate(layoutInflater)
        val btn=binding.btnTB
        val tb=binding.SpinnerTB
        val precio=binding.precioBoleto
        val cantidad=binding.maxBoletos
        setContentView(binding.root)

        val eid = "1Fm1ZppNqZfDBI5wdB2g"
        val repository = Repository(this)
        viewModel.getTBFilter(eid, repository)

        viewModel.dropdownList.observe(this, androidx.lifecycle.Observer {
            Log.d("dropdown list log", it.toString())
            val categoryString = arrayListOf<String>()
            for(name in it){
                categoryString.add("${name}")
            }
            val myadapter = ArrayAdapter<String>(this, R.layout.simple_spinner_item,categoryString)
            myadapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            binding.SpinnerTB.adapter = myadapter
        })

        btn.setOnClickListener {
            if((cantidad.text.toString().isNotEmpty())&&(precio.text.toString().isNotEmpty())){
                viewModel.AddEventoTipoBoleto(eid,tb.getSelectedItem().toString(), precio.text.toString().toInt(), cantidad.text.toString().toInt(),repository)
                var allAreaNames=arrayListOf<String>()
                allAreaNames.clear()
                val AreaAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, allAreaNames)
                AreaAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                binding.SpinnerTB.adapter = AreaAdapter
            }
            else{
                Toast.makeText(applicationContext, "Llena todos los campos antes de continuar.", Toast.LENGTH_SHORT).show()
            }

        }

    }
}