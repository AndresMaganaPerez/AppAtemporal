package com.example.appatemporal.framework.view.adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import com.example.appatemporal.databinding.FormularioPresupuestoBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.view.DeleteActivity
import com.example.appatemporal.framework.view.PresupuestoAndMeta
import com.example.appatemporal.framework.viewModel.PresupuestoOrganizadorViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModifyPresupuesto(private val viewModel: PresupuestoOrganizadorViewModel) :BottomSheetDialogFragment() {
    private lateinit var binding: FormularioPresupuestoBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            val data = arguments
            val idP = data!!.get("idProyecto").toString()
            val ganancia = data!!.get("ganancia").toString()
            val presupuesto = data!!.get("presupuesto").toString()
            val meta = data!!.get("meta").toString()
            val repository = Repository(requireContext())
            binding.descPresupuesto.hint="Presupuesto Actual: "+ presupuesto


            binding.saveButton.setOnClickListener {
                val presupuesto = binding.descPresupuesto.text.toString()
                if (presupuesto.isEmpty()){

                }
                else {
                    val presupuesto = binding.descPresupuesto.text.toString().toDouble()
                    viewModel.updatePrespuesto(presupuesto, idP.toInt(), repository)
                    saveAction()
                }
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FormularioPresupuestoBinding.inflate(inflater,container,false)
        return binding.root
    }

    private fun saveAction() {
        binding.descPresupuesto.setText("")
        dismiss()
    }
}
