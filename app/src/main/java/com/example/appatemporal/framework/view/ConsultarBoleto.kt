package com.example.appatemporal.framework.view

import android.app.Activity
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.appatemporal.R
import com.example.appatemporal.databinding.ActivityBoletoEspectadorBinding
import com.example.appatemporal.databinding.ActivityBoletoPorEventoBinding
import com.example.appatemporal.framework.viewModel.GetUserTicketViewModel
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class ConsultarBoleto : AppCompatActivity() {
    private val getTicketViewModel : GetUserTicketViewModel by viewModels()
    private lateinit var binding: ActivityBoletoEspectadorBinding
    //private lateinit var binding: BoletoEspectadorActivityBinding
    //private val repository = Repository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoletoEspectadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombre = intent.getStringExtra("nombre")
        val fecha = intent.getStringExtra("fecha")
        val hora = intent.getStringExtra("hora")
        val lugar = intent.getStringExtra("lugar")
        val direccion = intent.getStringExtra("direccion")
        val ciudad = intent.getStringExtra("ciudad")
        val estado = intent.getStringExtra("estado")
        val hashQr = intent.getStringExtra("hashQr")

        binding.NombreEventoBoleto.text = nombre
        binding.FechaEvento.text = fecha
        binding.LugarEvento.text = lugar
        binding.DireccionEvento.text = direccion
        binding.EstadoEvento.text = ciudad + ", " + estado

        val CODIGO_QR : BarcodeEncoder = BarcodeEncoder()
        val BITMAP: Bitmap =  CODIGO_QR.encodeBitmap(hashQr, BarcodeFormat.QR_CODE, 100, 100)
        var TARJETA_CODIGO_QR: ImageView = findViewById(R.id.CodigoQr)
        TARJETA_CODIGO_QR.setImageBitmap(BITMAP)

    }
}