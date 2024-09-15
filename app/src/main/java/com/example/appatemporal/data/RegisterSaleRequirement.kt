package com.example.appatemporal.data

import com.example.appatemporal.domain.Repository

class RegisterSaleRequirement {
    suspend operator fun invoke(id_Funcion : String, id_Metodo_Pago: String, id_Tipo_Boleto:String, repository: Repository){
        repository.registerSale(id_Funcion,id_Metodo_Pago, id_Tipo_Boleto)
    }
}
