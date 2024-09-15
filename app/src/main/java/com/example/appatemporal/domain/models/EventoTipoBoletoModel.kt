package com.example.appatemporal.domain.models
/**
 * This class is a model that will contain the data necessary
 * in order to add a new type of ticket successfully in the data base Firebase
 * @param nombre:String
 * @param id_Tipo_Boleto:String
 * @param max_boleto:Int
 * @param precio:Int
 *
 *  @author Resendiz & Camalich
 * */
class EventoTipoBoletoModel (
    val id_Evento: String,
    val id_Tipo_Boleto: String,
    val max_boleto: Int,
    val precio: Int
)