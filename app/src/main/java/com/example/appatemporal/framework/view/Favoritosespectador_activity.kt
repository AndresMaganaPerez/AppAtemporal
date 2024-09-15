package com.example.appatemporal.framework.view

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.databinding.ActivityMainFavoritosEspectadorBinding

class Favoritosespectador_activity : AppCompatActivity() {
    private lateinit var binding: ActivityMainFavoritosEspectadorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainFavoritosEspectadorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }
    @SuppressLint("WrongViewCast")
    private fun initRecyclerView(){
        val dataVertical: List<TarjetaGrande_favoritosespectador> = listOf(
            TarjetaGrande_favoritosespectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaGrande_favoritosespectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaGrande_favoritosespectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaGrande_favoritosespectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaGrande_favoritosespectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaGrande_favoritosespectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaGrande_favoritosespectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaGrande_favoritosespectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaGrande_favoritosespectador("Luis miguel","22/12/2022","Mexico")
        )

        val recyclerViewVerticalFavoritosEspectador  = binding.RVFavoritosEspectador
        val linearLayoutManager = LinearLayoutManager(this )
        linearLayoutManager.orientation=LinearLayoutManager.VERTICAL
        recyclerViewVerticalFavoritosEspectador.layoutManager = linearLayoutManager
        recyclerViewVerticalFavoritosEspectador.adapter = AdapterRVVerticalFavoritosEspectador(dataVertical)
    }
}