package com.example.appatemporal.framework.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.appatemporal.databinding.ActivityReportFailureBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.ReportFailureViewModel


class ReportFailureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportFailureBinding

    private val reportFailureViewModel: ReportFailureViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportFailureBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var repository = Repository(this)

        binding.btnSend.setOnClickListener {
            if (!binding.title.text.isNullOrEmpty() && !binding.description.text.isNullOrEmpty()) {
                val title: String = binding.title.text.toString()
                val description: String = binding.description.text.toString()
                reportFailureViewModel.addFailure(title, description, repository)
                val toast = Toast.makeText(this, "Reporte de falla envíada", Toast.LENGTH_SHORT)
                toast.show()
                binding.title.text.clear()
                binding.description.text.clear()
            } else {
                val toast = Toast.makeText(this, "No haz llenado todos los campos", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }
}