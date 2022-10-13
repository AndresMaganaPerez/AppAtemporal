package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository
/**
 * Class that invokes a function when called
 */
class RegisterSaleRequirement {
    /**
     * Invoke function that asks the repository to Register new Ticket Sale.
     * @param id_Funcion -> FunctionID to Register Sale to.
     * @param id_Metodo_Pago -> Payment Type Id.
     *@param id_Tipo_Boleto -> Ticket Type to Register.
     * @param repository -> Repository of the Application.
     *
     */
    suspend operator fun invoke(id_Funcion : String, id_Metodo_Pago: String, id_Tipo_Boleto:String, repository: Repository){
        repository.registerSale(id_Funcion,id_Metodo_Pago, id_Tipo_Boleto)
    }
}
