package com.example.appatemporal.framework.view

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appatemporal.R
import com.example.appatemporal.databinding.AddActivitiesBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.view.adapters.ActividadAdapter
import com.example.appatemporal.framework.view.adapters.ProjectsAdapter
import com.example.appatemporal.framework.viewModel.DeleteActivityViewModel
import kotlinx.android.synthetic.main.add_activities.*
import kotlinx.coroutines.launch
import java.util.*

class DeleteActivity : AppCompatActivity(){
    private val  viewModel : DeleteActivityViewModel by viewModels()
    private lateinit var binding: AddActivitiesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddActivitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var secondTipo = ""
        var tipo = ""

        val repository = Repository(this)
        var myExtras :Bundle? = intent.extras
        var idProyecto: Int=  myExtras?.getInt("id_proyecto")?:-1

        val doneActivities =  viewModel.countDoneActivities(repository, idProyecto, "Completado")
        val totalActivities = viewModel.countAllActivities(repository, idProyecto)

        if (doneActivities == totalActivities && doneActivities !=0 && totalActivities !=0){
            viewModel.updateEstatusCompletado(true, idProyecto, repository)
        }
        else{
            viewModel.updateEstatusCompletado(false, idProyecto, repository)
        }
        // get reference to the autocomplete text view
        val autocompleteTV2 = findViewById<AutoCompleteTextView>(R.id.spinnerFilterToFilter)

        val areaList = resources.getStringArray(R.array.areaList)
        val arrayAdapter2 = ArrayAdapter(this, R.layout.dropdown_menu, areaList)

        val estatusList = resources.getStringArray(R.array.estatusList)
        val arrayAdapter3 = ArrayAdapter(this, R.layout.dropdown_menu, estatusList)

        val prioridadList = resources.getStringArray(R.array.prioridadList)
        val arrayAdapter4 = ArrayAdapter(this, R.layout.dropdown_menu, prioridadList)

        //Predetermined Array, Do not erase it :)
        autocompleteTV2.setAdapter(arrayAdapter3)
        binding.spinnerFilterMain.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (binding.spinnerFilterMain.getText().toString()=="Area"){
                    // set adapter to the autocomplete tv to the arrayAdapter
                    autocompleteTV2.setAdapter(arrayAdapter2)

                    binding.spinnerFilterToFilter.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(s: Editable) {
                            if (binding.spinnerFilterToFilter.getText().toString()=="Negocios"){
                                secondTipo  = binding.spinnerFilterToFilter.getText().toString()
                                tipo = binding.spinnerFilterMain.getText().toString()
                                initRecyclerView(repository, idProyecto, secondTipo, tipo)
                            }
                            else if (binding.spinnerFilterToFilter.getText().toString()=="Personales"){
                                secondTipo  = binding.spinnerFilterToFilter.getText().toString()
                                tipo = binding.spinnerFilterMain.getText().toString()
                                initRecyclerView(repository, idProyecto, secondTipo, tipo)
                            }
                            else if (binding.spinnerFilterToFilter.getText().toString()=="Compras"){
                                secondTipo  = binding.spinnerFilterToFilter.getText().toString()
                                tipo = binding.spinnerFilterMain.getText().toString()
                                initRecyclerView(repository, idProyecto, secondTipo, tipo)
                            }
                            else if (binding.spinnerFilterToFilter.getText().toString()=="Mercancias"){
                                secondTipo  = binding.spinnerFilterToFilter.getText().toString()
                                tipo = binding.spinnerFilterMain.getText().toString()
                                initRecyclerView(repository, idProyecto, secondTipo, tipo)
                            }
                        }

                        override fun beforeTextChanged(s: CharSequence, start: Int,
                                                       count: Int, after: Int) {}

                        override fun onTextChanged(s: CharSequence, start: Int,
                                                   before: Int, count: Int) {}
                    })

                }
                else if (binding.spinnerFilterMain.getText().toString()=="Estatus"){
                    // set adapter to the autocomplete tv to the arrayAdapter
                    autocompleteTV2.setAdapter(arrayAdapter3)

                    binding.spinnerFilterToFilter.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(s: Editable) {
                            if (binding.spinnerFilterToFilter.getText().toString()=="En Proceso"){
                                secondTipo  = binding.spinnerFilterToFilter.getText().toString()
                                tipo = binding.spinnerFilterMain.getText().toString()
                                initRecyclerView(repository, idProyecto, secondTipo, tipo)
                            }
                            else if (binding.spinnerFilterToFilter.getText().toString()=="No Completado"){
                                secondTipo  = binding.spinnerFilterToFilter.getText().toString()
                                tipo = binding.spinnerFilterMain.getText().toString()
                                initRecyclerView(repository, idProyecto, secondTipo, tipo)
                            }
                            else if (binding.spinnerFilterToFilter.getText().toString()=="Completado"){
                                secondTipo  = binding.spinnerFilterToFilter.getText().toString()
                                tipo = binding.spinnerFilterMain.getText().toString()
                                initRecyclerView(repository, idProyecto, secondTipo, tipo)
                            }
                        }
                        override fun beforeTextChanged(s: CharSequence, start: Int,
                                                       count: Int, after: Int) {}

                        override fun onTextChanged(s: CharSequence, start: Int,
                                                   before: Int, count: Int) {}
                    })

                    //binding.filterButton.setOnClickListener{
                    //     Log.d("hola",prioridad)
                    //     Log.d("hola", tipo)
                    //     filterRecyclerView(repository,idProyecto, prioridad, tipo)
                    //}

                }

                else if (binding.spinnerFilterMain.getText().toString()=="Prioridad"){
                    // set adapter to the autocomplete tv to the arrayAdapter
                    autocompleteTV2.setAdapter(arrayAdapter4)

                    binding.spinnerFilterToFilter.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(s: Editable) {
                            if (binding.spinnerFilterToFilter.getText().toString()=="Alta"){
                                secondTipo  = binding.spinnerFilterToFilter.getText().toString()
                                tipo = binding.spinnerFilterMain.getText().toString()
                                initRecyclerView(repository, idProyecto, secondTipo, tipo)
                            }
                            else if (binding.spinnerFilterToFilter.getText().toString()=="Media"){
                                secondTipo  = binding.spinnerFilterToFilter.getText().toString()
                                tipo = binding.spinnerFilterMain.getText().toString()
                                initRecyclerView(repository, idProyecto, secondTipo, tipo)
                            }
                            else if (binding.spinnerFilterToFilter.getText().toString()=="Baja"){
                                secondTipo  = binding.spinnerFilterToFilter.getText().toString()
                                tipo = binding.spinnerFilterMain.getText().toString()
                                initRecyclerView(repository, idProyecto, secondTipo, tipo)
                            }

                        }

                        override fun beforeTextChanged(s: CharSequence, start: Int,
                                                       count: Int, after: Int) {}

                        override fun onTextChanged(s: CharSequence, start: Int,
                                                   before: Int, count: Int) {}
                    })

                    //binding.filterButton.setOnClickListener{
                   //     Log.d("hola",prioridad)
                   //     Log.d("hola", tipo)
                   //     filterRecyclerView(repository,idProyecto, prioridad, tipo)
                    //}




                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {}
        })

        initRecyclerView(repository, idProyecto, secondTipo, tipo)
        binding.newTaskButton.setOnClickListener {
            val intent = Intent(this, AddNewActivityForm::class.java)
            with(intent){
                putExtra("id_proyecto", idProyecto)
            }
            startActivity(intent)
        }

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
        // get reference to the string array that we just created
        val filterList = resources.getStringArray(R.array.filterList)
        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        val arrayAdapter1 = ArrayAdapter(this, R.layout.dropdown_menu, filterList)
        // get reference to the autocomplete text view
        val autocompleteTV1 = findViewById<AutoCompleteTextView>(R.id.spinnerFilterMain)
        // set adapter to the autocomplete tv to the arrayAdapter
        autocompleteTV1.setAdapter(arrayAdapter1)
    }


    private fun initRecyclerView( repository: Repository,id: Int, SecondTipo: String, tipo:String) {
        if (tipo == "Area"){
            viewModel.getAllActivitiesArea(id,SecondTipo, repository)
        }
        else if (tipo == "Estatus"){
            viewModel.getAllActivitiesEstatus(id,SecondTipo, repository)
        }
        else if (tipo == "Prioridad"){
            viewModel.getAllActivitiesPrioridad(id,SecondTipo, repository)
        }
        else{
            viewModel.getAllActivitiesid(id,repository)
        }
        viewModel.activities.observe(this, Observer { activityList ->
            if (activityList.isEmpty()){
                Toast.makeText(this, "No se encontraron resultados", Toast.LENGTH_SHORT).show()
            }
            binding.todoRv.layoutManager = LinearLayoutManager(this)
            binding.todoRv.adapter = ActividadAdapter(activityList, viewModel)
        })
    }

}