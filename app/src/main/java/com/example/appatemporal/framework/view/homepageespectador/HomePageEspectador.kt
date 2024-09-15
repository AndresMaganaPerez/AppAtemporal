package com.example.appatemporal.framework.view.homepageespectador

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.databinding.HomepageEspectadorBinding
import com.example.appatemporal.framework.view.homepageespectador.recyclerview.AdapterRVHorizontal
import com.example.appatemporal.framework.view.homepageespectador.recyclerview.AdapterRVVertical

class HomePageEspectador : AppCompatActivity() {
    private lateinit var binding: HomepageEspectadorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomepageEspectadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
    }
    @SuppressLint("WrongViewCast")
    private fun initRecyclerView(){
        val dataVertical: List<TarjetaGrande_espectador> = listOf(
            TarjetaGrande_espectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaGrande_espectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaGrande_espectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaGrande_espectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaGrande_espectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaGrande_espectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaGrande_espectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaGrande_espectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaGrande_espectador("Luis miguel","22/12/2022","Mexico")
        )
        val dataHorizontal: List<TarjetaChica_espectador> = listOf(
            TarjetaChica_espectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaChica_espectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaChica_espectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaChica_espectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaChica_espectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaChica_espectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaChica_espectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaChica_espectador("Luis miguel","22/12/2022","Mexico"),
            TarjetaChica_espectador("Luis miguel","22/12/2022","Mexico")
        )

        val recyclerViewVertical  =  binding.VerticalViewEspectador
        val recyclerViewHorizontal = binding.HorizontalViewEspectador
        val linearLayoutManager = LinearLayoutManager(this )
        val linearLayoutManagerHorizontal = LinearLayoutManager(this )
        linearLayoutManager.orientation=LinearLayoutManager.VERTICAL
        linearLayoutManagerHorizontal.orientation=LinearLayoutManager.HORIZONTAL //cambiar orientacion
        recyclerViewVertical.layoutManager = linearLayoutManager
        recyclerViewVertical.adapter = AdapterRVVertical(dataVertical)

        recyclerViewHorizontal.layoutManager = linearLayoutManagerHorizontal
        recyclerViewHorizontal.adapter = AdapterRVHorizontal(dataHorizontal)
    }
}