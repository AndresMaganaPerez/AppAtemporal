package com.example.appatemporal.framework.view.homepageespectador

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appatemporal.R
import com.example.appatemporal.framework.view.BoletoPorEventoActivity
import com.example.appatemporal.framework.view.homepageespectador.recyclerview.AdapterRVHorizontal
import com.example.appatemporal.framework.view.homepageespectador.recyclerview.AdapterRVVertical

class homepage_espectador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage_espectador)
        initRecyclerView()

        val Buttontickets_icon = findViewById<ImageView>(R.id.tickets_icon)
        Buttontickets_icon.setOnClickListener{
            val myIntent = Intent(this, BoletoPorEventoActivity::class.java)
            startActivity(myIntent)
        }
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
        val recyclerViewVertical  = findViewById<RecyclerView>(R.id.VerticalView_Espectador)
        val recyclerViewHorizontal = findViewById<RecyclerView>(R.id.HorizontalView_Espectador)
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