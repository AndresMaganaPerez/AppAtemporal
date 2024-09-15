package com.example.appatemporal.framework.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.appatemporal.databinding.ActivityRegisterQrviewBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.MainViewModel
import com.example.appatemporal.framework.viewModel.ScanQRViewModel
import com.google.zxing.integration.android.IntentIntegrator

class RegisterQRView : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterQrviewBinding

    private val scanQRViewModel : ScanQRViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterQrviewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // ----------------------------Navbar------------------------------------
        val userRole = getSharedPreferences("user", Context.MODE_PRIVATE).getString("rol", "").toString()

        // Visibility
        if (userRole != "Organizador") {
            binding.navbar.budgetIcon.visibility = android.view.View.GONE
            binding.navbar.metricsIcon.visibility = android.view.View.GONE
            binding.navbar.budgetText.visibility = android.view.View.GONE
            binding.navbar.metricsText.visibility = android.view.View.GONE
        }
        if (userRole == "Ayudante") {
            binding.navbar.eventsIcon.visibility = android.view.View.GONE
            binding.navbar.eventsText.visibility = android.view.View.GONE
        }

        // Intents
        binding.navbar.homeIcon.setOnClickListener {
            if(userRole == "Organizador"){
                val intent = Intent(this, ActivityMainHomepageOrganizador::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, ActivityMainHomepageEspectador::class.java)
                startActivity(intent)
            }
        }

        binding.navbar.eventsIcon.setOnClickListener {
            if(userRole == "Organizador"){
                val intent = Intent(this,ActivityMisEventosOrganizador::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this,CategoriasEventos::class.java)
                startActivity(intent)
            }
        }

        binding.navbar.budgetIcon.setOnClickListener {
            val intent = Intent(this, ProyectoOrganizador::class.java)
            startActivity(intent)
        }

        binding.navbar.ticketsIcon.setOnClickListener {
            if (userRole == "Espectador" || userRole == "Organizador") {
                val intent = Intent(this, BoletoPorEventoActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, RegisterQRView::class.java)
                startActivity(intent)
            }
        }

        binding.navbar.metricsIcon.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

        val btnQR = binding.btnConsulta
        btnQR.setOnClickListener{
            initScanner()
        }
    }

    private fun initScanner(){
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Escanea el boleto")
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        val repository = Repository(this)


        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "No se encontró un valor.", Toast.LENGTH_SHORT).show()
            } else {
                scanQRViewModel.verifyTicketExistence(result.contents, repository) { existence ->
                    if (existence) {
                        scanQRViewModel.updateTicketValue(result.contents, repository) { status ->
                            if(status){
                                Toast.makeText(this,"Boleto válido escaneado EXITÓSAMENTE",Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this,"Boleto YA USADO",Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this,"Boleto INVÁLIDO",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}