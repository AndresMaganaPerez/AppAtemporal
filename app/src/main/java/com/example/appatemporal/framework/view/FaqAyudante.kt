package com.example.appatemporal.framework.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.appatemporal.databinding.ActivityFaqAyudanteBinding
import com.example.appatemporal.databinding.ActivityFaqOrganizadorBinding

class FaqAyudante : AppCompatActivity() {
    private lateinit var binding: ActivityFaqAyudanteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqAyudanteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnQ1.setOnClickListener {
            if (binding.tvQ1.visibility == View.GONE) {
                binding.tvQ1.visibility = View.VISIBLE
            } else {
                binding.tvQ1.visibility = View.GONE
            }
        }

        binding.btnQ2.setOnClickListener {
            if (binding.tvQ2.visibility == View.GONE) {
                binding.tvQ2.visibility = View.VISIBLE
            } else {
                binding.tvQ2.visibility = View.GONE
            }
        }

        binding.btnQ3.setOnClickListener {
            if (binding.tvQ3.visibility == View.GONE) {
                binding.tvQ3.visibility = View.VISIBLE
            } else {
                binding.tvQ3.visibility = View.GONE
            }
        }

        binding.btnQ4.setOnClickListener {
            if (binding.tvQ4.visibility == View.GONE) {
                binding.tvQ4.visibility = View.VISIBLE
            } else {
                binding.tvQ4.visibility = View.GONE
            }
        }
    }
}
