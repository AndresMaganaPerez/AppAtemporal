package com.example.appatemporal.framework.view

import android.R
import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_add_tipo_boleto.*

/**
 * This file is linked with activity_add_tipo_boleto.xml
 * This file is in charge of controlling the logic behind the funtion add a type of ticket
 *
 * @see activity_add_tipo_boleto.xml
 *
 * @author Resendiz & Camalich
 *
 * */

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

        val idEvent = intent.getStringExtra("idEvent")
        val eid = idEvent.toString()
        val repository = Repository(this)
        viewModel.getTBFilter(eid, repository)

        viewModel.dropdownList.observe(this, androidx.lifecycle.Observer {
            Log.d("dropdown list log", it.toString())
            val TBString = arrayListOf<String>()
            for(name in it){
                TBString.add("${name}")
            }
            val myadapter = ArrayAdapter<String>(this, R.layout.simple_spinner_item,TBString)
            myadapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            binding.SpinnerTB.adapter = myadapter
        })

            btn.setOnClickListener {
                if(SpinnerTB.getCount()==0){
                    Toast.makeText(applicationContext, "No existen tipos de boleto disponibles para tu evento.", Toast.LENGTH_SHORT).show()
                    btn.isClickable=false
                }
                else{
                    if((cantidad.text.toString().isNotEmpty())&&(precio.text.toString().isNotEmpty())){
                        viewModel.AddEventoTipoBoleto(eid,tb.getSelectedItem().toString(), precio.text.toString().toInt(), cantidad.text.toString().toInt(),repository)
                        var allAreaNames=arrayListOf<String>()
                        allAreaNames.clear()
                        val AreaAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, allAreaNames)
                        AreaAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                        binding.SpinnerTB.adapter = AreaAdapter
                        val submitBtn =  Intent(this, ActivityMisEventosOrganizador::class.java)
                        this.startActivity(submitBtn)
                    }
                    else{
                        Toast.makeText(applicationContext, "Llena todos los campos antes de continuar.", Toast.LENGTH_SHORT).show()
                    }


                }
            }
    }
}