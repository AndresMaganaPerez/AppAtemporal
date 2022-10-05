package com.example.appatemporal.framework.view

import android.R
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.appatemporal.databinding.ActivityAddCategoriaBinding
import com.example.appatemporal.databinding.ActivityCrearEventoBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.AddNewEventViewModel
import com.example.appatemporal.framework.viewModel.GetCategoryFilterViewModel

class AcivityAddCategoria : AppCompatActivity() {
    private val viewModel: GetCategoryFilterViewModel by viewModels()
    private lateinit var binding: ActivityAddCategoriaBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityAddCategoriaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val eid = "2OfJ55F0AkZj7sXvTloC"
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
    }
}