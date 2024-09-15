package com.example.appatemporal.domain.models
/**
 * This class is a model that will contain the data necessary
 * in order to add a function associated with an event successfully in the data base Firebase
 * @param fecha_funcion:String
 * @param hora_inicio:String
 * @param hora_fin:Int
 *
 *  @author Resendiz & Camalich
 * */
data class FuncionModel(
    var fecha_funcion : String,
    var hora_inicio : String,
    var hora_fin : String
)
