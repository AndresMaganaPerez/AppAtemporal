package com.example.appatemporal.framework.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.GetCurrentTicketsFunRequirement
import com.example.appatemporal.data.GetDropDownNamesRequirement
import com.example.appatemporal.data.GetMetodoPagoUidRequirement
import com.example.appatemporal.data.RegisterSaleRequirement
import com.example.appatemporal.domain.Repository
import kotlinx.coroutines.launch
/**
 * Class that inherits from ViewModel
 */
class RegisterSaleViewModel : ViewModel() {
    private val getDropDownNamesRequirement = GetDropDownNamesRequirement()
    private val getCurrentTicketsFunRequirement = GetCurrentTicketsFunRequirement()
    private val registerSaleRequirement = RegisterSaleRequirement()
    private val getMetodoPagoUidRequirement = GetMetodoPagoUidRequirement()

    val dropdownList = MutableLiveData<ArrayList<Triple<String, Int, String>>>()
    val ticketAvailability = MutableLiveData<Pair<Int, Int>>()

    /**
     * Function calling getDropDownNamesRequirement to obtain the TicketTypes of the given event. Updates a MutableLiveData with an array filled with Triples
     * of TicketTypes.
     * @param idEvent -> Event ID of event to take TicketTypes of.
     * @param repository -> @param repository -> Repository of the Application.
     */
    fun getDropdownNames(idEvent: String, repository: Repository) {
        viewModelScope.launch {
            val dropdownValues = getDropDownNamesRequirement(idEvent, repository)
            val dropdownArray = arrayListOf<Triple<String, Int, String>>()
            for (list in dropdownValues) {
                dropdownArray.add(list)
            }
            dropdownList.postValue(dropdownArray)
        }
    }

    /**
     * Function calling getCurrentTicketsFunRequirement to obtain the Availability of the specific TicketType to Register Sale to.
     * Stores Maximum Value and count of specific ticket type, and sends it to a MutableLiveData.
     * @param idTicketType: String ->
     * @param idEvento:String -> Event ID of event to take TicketTypes of.
     * @param idFuncion: String -> Function ID of Function to take tickets from.
     * @param repository -> Repository of the Application.
     */
    fun getTicketAvailability(idTicketType: String, idEvento: String, idFuncion: String, repository: Repository) {
        viewModelScope.launch {
            val ticketsData = getCurrentTicketsFunRequirement(idEvento, idFuncion, repository)
            for (ticket in ticketsData) {
                if (ticket.first == idTicketType) {
                    val ticketInfo = Pair(ticket.second, ticket.third)
                    ticketAvailability.postValue(ticketInfo)
                    break
                }
            }
        }
    }
    /**
     * Function calling getMetodoPagoUidRequirement to obtain the UID of the Selected PaymentType.
     * @param metodoPago Event Id to get TicketTypes from.
     * @param repository -> Repository of the Application.
     */
    fun getMetodoPagoUid(metodoPago: String, repository: Repository, result: (String) -> Unit) {
        viewModelScope.launch {
            val queryResult = getMetodoPagoUidRequirement(metodoPago, repository)
            var metodoPagoUid: String = queryResult.documents[0].id
            result(metodoPagoUid)
        }
    }
    /**
     * Function calling RegisterSaleRequirement to store new Registry of Ticket In DB.
     * @param idFuncion -> FunctionID to Register Sale to.
     * @param id_Metodo_Pago -> Payment Type Id.
     *@param id_Tipo_Boleto -> Ticket Type to Register.
     * @param repository -> Repository of the Application.
     */
   fun RegisterSale(idFuncion: String,id_Metodo_Pago:String, id_Tipo_Boleto :String, repository: Repository){
        viewModelScope.launch{
            registerSaleRequirement(idFuncion,id_Metodo_Pago,id_Tipo_Boleto, repository)
        }
    }

}