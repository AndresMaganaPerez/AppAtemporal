package com.example.appatemporal.framework.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appatemporal.databinding.ActivityTermsCondBinding

class TermsCond : AppCompatActivity() {

    private lateinit var binding: ActivityTermsCondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsCondBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnReturnRegister.setOnClickListener {
            finish()
        }
    }
}