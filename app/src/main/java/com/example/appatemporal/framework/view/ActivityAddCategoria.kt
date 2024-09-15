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
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.GetCategoryFilterViewModel

/**
 * This file is linked with activity_add_categoria.xml
 * This file is in charge of controlling the logic behind the funtion bind a category with an event
 *
 * @see activity_add_categoria.xml
 *
 * @author Resendiz & Camalich
 *
 * */

class ActivityAddCategoria : AppCompatActivity() {
    private val viewModel: GetCategoryFilterViewModel by viewModels()
    private lateinit var binding: ActivityAddCategoriaBinding
    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCategoriaBinding.inflate(layoutInflater)
        val btn=binding.btnCategoria
        val categoria=binding.SpinnerCategoria
        setContentView(binding.root)

        val idEvent = intent.getStringExtra("idEvent")

        val eid = idEvent.toString()
        val repository = Repository(this)
        viewModel.getCategoryFilter(eid, repository)

        viewModel.dropdownList.observe(this, androidx.lifecycle.Observer {
            Log.d("dropdown list log", it.toString())
            val categoryString = arrayListOf<String>()
            for(name in it){
                categoryString.add("${name}")
            }
            val myadapter = ArrayAdapter<String>(this, R.layout.simple_spinner_item,categoryString)
            myadapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            binding.SpinnerCategoria.adapter = myadapter
        })

            btn.setOnClickListener {
                if(categoria.getCount()==0){
                    Toast.makeText(applicationContext, "No existen categorías disponibles para tu evento.", Toast.LENGTH_SHORT).show()
                    btn.isClickable=false
                }
                else{
                    viewModel.addEventoCategoria(eid,categoria.getSelectedItem().toString(), repository)
                    var allAreaNames=arrayListOf<String>()
                    allAreaNames.clear()
                    val AreaAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, allAreaNames)
                    AreaAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                    binding.SpinnerCategoria.adapter = AreaAdapter

                    val submitBtn =  Intent(this, ActivityMisEventosOrganizador::class.java)
                    this.startActivity(submitBtn)
                }

            }


    }
}