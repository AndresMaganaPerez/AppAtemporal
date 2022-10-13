package com.example.appatemporal.framework.view

import android.R
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.appatemporal.databinding.ActivityRegisterSaleBinding
import com.example.appatemporal.domain.Repository
import com.example.appatemporal.framework.viewModel.RegisterSaleViewModel


/**
 * Class that inherits from AppCompactActivity, in charge of Displaying RegisterSale.
 *
 */
class RegisterSale : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterSaleBinding
    private val registerSaleViewModel: RegisterSaleViewModel by viewModels()
    private var ticketType = arrayListOf<String>()
    private lateinit var idEvent: String
    private lateinit var idFuncion: String
    private val activityContext = this
    private lateinit var repository : Repository

    /**
     * Overrides function onCreate and starts the activity
     *
     * @param savedInstanceState: Bundle? -> Saved instance of the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterSaleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navbar.homeIcon.setOnClickListener {
            finish()
        }

        binding.navbar.budgetIcon.setOnClickListener {
            val intent = Intent(this, ProyectoOrganizador::class.java)
            startActivity(intent)
        }

        binding.navbar.ticketsIcon.setOnClickListener {
            finish()
        }

        binding.navbar.metricsIcon.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

        val mSpinner2 = binding.spinner2
        val mSpinner3 = binding.spinner3

        repository = Repository(this)

        idEvent = "Nbb94T1aTzqT4RiXfmWm" //Hardcoded Event ID
        idFuncion = "ww0LP40KOfktWEE4fcDz"

        /**
         * Calls ViewModel to get TicketType Dropdown.
         */
        registerSaleViewModel.getDropdownNames(idEvent, Repository(this))
        registerSaleViewModel.dropdownList.observe(this, Observer {
            val ticketTypeString = arrayListOf<String>()
            for (name in it) {
                ticketTypeString.add("${name.first}: $${name.second}")
                ticketType.add("${name.third}")
            }
            val mArrayAdapter2 = ArrayAdapter<String>(this, R.layout.simple_spinner_dropdown_item, ticketTypeString)
            mArrayAdapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            mSpinner2.adapter = mArrayAdapter2
        })

        val payment = arrayListOf<String>("Efectivo", "Tarjeta")

        val mArrayAdapter3 = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, payment)
        mArrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        mSpinner3.adapter = mArrayAdapter3

        binding.spinner2.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            /**
             * Overrides function onItemSelected, obtains spinner selection.
             *
             * @param p0: AdapterView<*>? -> Given Adapter where selection happened.
             * @param p1: View? -> The spinner to take input from.
             * @param p2: Int -> Index/Position of Selected Item.
             * @param p3: Long -> ID of row.
             */
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                /**
                 * Calls Viewmodel to get count of tickets and max number for the ticket type. If ticket count is less than max, enable button, allowing registry.
                 */
                registerSaleViewModel.getTicketAvailability(ticketType[p2], idEvent, idFuncion, Repository(activityContext))
                registerSaleViewModel.ticketAvailability.observe(activityContext, Observer {
                    if (it.first < it.second) {
                        binding.btnRegisterSale.setBackgroundColor(Color.BLUE)
                        binding.btnRegisterSale.isEnabled = true

                        binding.btnRegisterSale.setOnClickListener{
                            /**
                             * Get Selected item of PaymentTypeSpinner and calls Firebase Query to register sale with given info, on button click.
                             */
                            registerSaleViewModel.getMetodoPagoUid(binding.spinner3.selectedItem.toString(), Repository(activityContext)) { uid ->
                                registerSaleViewModel.RegisterSale(idFuncion,uid, ticketType[p2], Repository(activityContext))
                                Toast.makeText(activityContext, "Venta registrada exitósamente", Toast.LENGTH_SHORT).show()
                                registerSaleViewModel.getTicketAvailability(ticketType[p2], idEvent, idFuncion, Repository(activityContext))
                                registerSaleViewModel.ticketAvailability.observe(activityContext, Observer {
                                    /**
                                     * Checks ticket availability again, on button press.
                                     */
                                    if (it.first < it.second) {
                                        binding.btnRegisterSale.setBackgroundColor(Color.BLUE)
                                        binding.btnRegisterSale.isEnabled = true
                                    } else {
                                        binding.btnRegisterSale.isEnabled = false
                                        binding.btnRegisterSale.setBackgroundColor(Color.RED)

                                    }
                                })
                            }
                        }
                    } else {
                        binding.btnRegisterSale.isEnabled = false
                        binding.btnRegisterSale.setBackgroundColor(Color.RED)
                    }
                })
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }
}