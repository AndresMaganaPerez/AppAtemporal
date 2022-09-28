package com.example.appatemporal.domain

import android.util.Log
import com.example.appatemporal.domain.models.GetTicketModel
import com.example.appatemporal.domain.models.ReportFailureModel
import com.example.appatemporal.domain.models.UserModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.example.appatemporal.domain.models.TicketModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Integer.parseInt
import java.util.*
import kotlin.collections.ArrayList

class FirestoreService {
    private val db = Firebase.firestore

    suspend fun addUser(uid: String, user: UserModel) {
        db.collection("Usuario")
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                Log.d("FirestoreLogs","Added User Correctly")
            }
            .await()
    }

    suspend fun addUserRole(uid: String, role: String) {
        val dbRole = db.collection("Rol")
            .whereEqualTo("nombre_Rol", role)
            .get()
            .addOnSuccessListener {
                Log.d("FirestoreLogs","Got Role Correctly: ${it.documents[0].id}")
            }.await()

        val userRole = hashMapOf(
            "id_Usuario" to uid,
            "id_Rol" to dbRole.documents[0].id
        )

        db.collection("Usuario_Rol")
            .add(userRole)
            .addOnSuccessListener {
                Log.d("FirestoreLogs","Added User wih Role Correctly")
            }
            .addOnFailureListener {
                Log.d("FirestoreLogs","Added user failed, exception: $it")
            }
    }

    suspend fun verifyUser(uid: String) : Boolean {
        var userExists = false
        db.collection("Usuario")
            .document(uid)
            .get()
            .addOnSuccessListener { documentRef ->
                if (documentRef.exists()) {
                    userExists = true
                }
            }
            .await()
        return userExists
    }

    suspend fun getUser(uid: String) : DocumentSnapshot {
        var userData: DocumentSnapshot =
            db.collection("Usuario")
            .document(uid)
            .get()
            .await()
        return userData
    }

    suspend fun getUserRole(uid: String) : DocumentSnapshot {
        var dbRole: QuerySnapshot =
            db.collection("Usuario_Rol")
                .whereEqualTo("id_Usuario", uid)
                .get()
                .await()
        var userRole: DocumentSnapshot =
            db.collection("Rol")
                .document(dbRole.documents[0].data?.get("id_Rol").toString())
                .get()
                .await()
        return userRole
    }

    // Que evento le corresponde al boleto
    // uid: userId, eid: eventId, fid: funcionId
    // getUserTicket

    suspend fun getUserTickets(uid : String) : MutableList<GetTicketModel> {
        var result : MutableList<GetTicketModel> = arrayListOf()
        var ticket : GetTicketModel = GetTicketModel()
        var boletos : QuerySnapshot =
            db.collection("Boleto")
                .whereEqualTo("id_Usuario",uid)
                .get()
                .await()
        for (boleto in boletos){
            var funciones : QuerySnapshot =
                db.collection("Funcion")
                    .whereEqualTo(FieldPath.documentId(),boleto.data?.get("id_Funcion"))
                    .get()
                    .await()
            var evento : QuerySnapshot =
                db.collection("Evento")
                    .whereEqualTo(FieldPath.documentId(),funciones.documents[0].data?.get("id_Evento"))
                    .get()
                    .await()
            ticket.nombre_evento = evento.documents[0].data?.get("nombre_Evento").toString()
            ticket.fecha = funciones.documents[0].data?.get("fecha").toString()
            ticket.horario = funciones.documents[0].data?.get("hora_Inicio").toString()
            ticket.lugar = evento.documents[0].data?.get("nombre_Ubicacion").toString()
            ticket.direccion = evento.documents[0].data?.get("direccion").toString()
            ticket.ciudad = evento.documents[0].data?.get("ciudad").toString()
            ticket.estado = evento.documents[0].data?.get("estado").toString()
            ticket.hash_qr = boleto.data?.get("hash_QR").toString()

            result.add(ticket)

            //Log.d("LOG ticket",ticket.toString())
        }
        //Log.d("LOG aqui",result.isEmpty().toString())
        return result
    }

    suspend fun eventCount(uid: String) : Int {
        var events : QuerySnapshot =
            db.collection("Usuario_Evento")
                .whereEqualTo("id_Usuario", uid)
                .get()
                .await()
        return events.count()
    }

    suspend fun ventasCount(uid : String) : Pair<Int, Int> {
        var ventasCount : Int = 0
        var asistenciasCount : Int = 0
        var ventas : QuerySnapshot =
            db.collection("Usuario_Evento")
                .whereEqualTo("id_Usuario", uid)
                .get()
                .await()
        for (document in ventas){
            var funciones : QuerySnapshot =
                db.collection("Funcion")
                    .whereEqualTo("id_Evento",document.data?.get("id_Evento"))
                    .get()
                    .await()
            for (document in funciones){
                var boletosAuxVentas : QuerySnapshot =
                    db.collection("Boleto")
                        .whereEqualTo("id_Funcion", document.id)
                        .get()
                        .await()
                ventasCount += boletosAuxVentas.count()
            }
            for (document in funciones){
                var boletosAuxAsistencias : QuerySnapshot =
                    db.collection("Boleto")
                        .whereEqualTo("id_Funcion", document.id)
                        .whereEqualTo("activo", false)
                        .get()
                        .await()
                asistenciasCount += boletosAuxAsistencias.count()
            }
        }
        val result = Pair(ventasCount, asistenciasCount)
        return result
    }

    suspend fun getRating(uid: String) : Float {
        var acumulado = 0f
        var count = 0f
        var events : QuerySnapshot =
            db.collection("Usuario_Evento")
                .whereEqualTo("id_Usuario", uid)
                .get()
                .await()
        for (document in events){
            var feedbacks : QuerySnapshot =
                db.collection("Feedback")
                .whereEqualTo("id_Evento",document.data?.get("id_Evento"))
                .get()
                .await()
            for (document in feedbacks){
                acumulado += document.data?.get("rating").toString().toInt()
                count += 1
            }
        }
        if (count <= 0) return 0f
        return (acumulado/count).toFloat()
    }

    suspend fun getRevenue(uid: String) : Int {
        var ventaTotal = 0
        var boletos: QuerySnapshot
        var tiposBoleto: QuerySnapshot
        var events: QuerySnapshot =
            db.collection("Usuario_Evento")
                .whereEqualTo("id_Usuario", uid)
                .get()
                .await()
        for (document in events) {
            var funciones: QuerySnapshot =
                db.collection("Funcion")
                    .whereEqualTo("id_Evento", document.data?.get("id_Evento"))
                    .get()
                    .await()
            for (document in funciones) {
                boletos =
                    db.collection("Boleto")
                        .whereEqualTo("id_Funcion", document.id)
                        .get()
                        .await()
                //Log.d("LOG boletos", boletos.count().toString())
                tiposBoleto =
                    db.collection("Evento_Tipo_Boleto")
                        .whereEqualTo("id_Evento", document.data?.get("id_Evento"))
                        .get()
                        .await()
                for (tipoBoleto in tiposBoleto) {
                    for (document in boletos) {
                        if (document.data?.get("id_Tipo_Boleto") == tipoBoleto.data?.get("id_Tipo_Boleto")) {
                            Log.d("IF de los boletos", tipoBoleto.data?.get("precio").toString())
                            ventaTotal += tipoBoleto.data?.get("precio").toString().toInt()
                        }
                        //Log.d("LOG for boletos", document.id.toString())
                    }
                }
            }
        }
        return ventaTotal
    }

    suspend fun updateTicketValue(resulted: String): Boolean {
        var result: String = resulted

        var exito: Boolean = false

        var Queryresult: Boolean = true

        db.collection("Boleto")
            .whereEqualTo("hash_QR", result)
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    Queryresult = document.getField<Boolean>("activo") as Boolean
                    if (Queryresult == true) {
                        db.collection("Boleto").document(document.id).update("activo", false)
                        exito = true
                    } else {
                        exito = false
                    }
                }
            }
            .await()
        return exito
    }

    suspend fun getTicketDropDown(idEvent: String) : List<Triple<String, Int, String>> {
        var dropDown : MutableList<Triple<String, Int, String>> = mutableListOf()
        val ticketInfo = db.collection("Evento_Tipo_Boleto")
            .whereEqualTo("id_Evento", idEvent)
            .get()
            .await()
        for (id in ticketInfo) {
            var info = id.getField<String>("id_Tipo_Boleto").toString()
            var precio = id.getField<Int>("precio") as Int
            val name = db.collection("Tipo_Boleto")
                .document(info)
                .get()
                .await()
            dropDown.add(Triple(name.data?.get("nombre_Tipo_Boleto").toString(), precio, name.id))
        }
        return dropDown
    }

    suspend fun currentTicketsFun(idEvent: String, idFuncion: String) : List<Triple<String, Int, Int>> {
        val maxCountEvent: MutableList<Triple<String, Int, Int>> = mutableListOf()
        val tipoEventoBoleto = db.collection("Evento_Tipo_Boleto")
            .whereEqualTo("id_Evento", idEvent)
            .get()
            .await()
        for (document in tipoEventoBoleto) {
            val boletosEventoTipo = db.collection("Boleto")
                .whereEqualTo("id_Funcion", idFuncion)
                .whereEqualTo("id_Tipo_Boleto", document.data.get("id_Tipo_Boleto"))
                .get()
                .await()
            maxCountEvent.add(Triple(document.data?.get("id_Tipo_Boleto").toString(), boletosEventoTipo.documents.size, parseInt(document.data?.get("max_Boletos").toString())))
        }
        return maxCountEvent
    }

    suspend fun RegisterSale(idFuncion: String, id_Metodo_Pago: String,id_Tipo_Boleto : String){
        var currentDate = Date()
        db.collection("Boleto")
            .document()
            .set(TicketModel(true,"RegistroEnTaquilla",idFuncion, id_Metodo_Pago,id_Tipo_Boleto,currentDate,currentDate))
            .await()
    }

    /**
     * Adds a document in ReporteFallas collection of Firestore
     * @param title: String
     * @param description: String
     */
    suspend fun addFailure(title: String, description: String) {
        val failure = ReportFailureModel(title, description)
        db.collection("ReporteFallas")
            .add(failure)
            .addOnSuccessListener {
                Log.d("Firestore Log Failure", "Success")
            }.await()
    }

    suspend fun getEventName(eid:String) : String {
        var event : DocumentSnapshot =
            db.collection("Evento")
                .document(eid)
                .get()
                .await()
        return event.data?.get("nombre_Evento").toString()
    }

    suspend fun generalProfitsEvent(eid:String) : Int {

        var ganancias = 0
        var boletos: QuerySnapshot
        var tiposBoleto: QuerySnapshot

        var funciones: QuerySnapshot = db.collection("Funcion")
            .whereEqualTo("id_Evento", eid)
            .get()
            .await()
        Log.d("generalProfitsEvent-Funciones", funciones.count().toString())
        for (element in funciones) {
            boletos = db.collection("Boleto")
                    .whereEqualTo("id_Funcion", element.id)
                    .get()
                    .await()
            Log.d("generalProfitsEvent-Boletos", boletos.count().toString())
            tiposBoleto =
                db.collection("Evento_Tipo_Boleto")
                    .whereEqualTo("id_Evento", element.data?.get("id_Evento"))
                    .get()
                    .await()
            Log.d("generalProfitsEvent-tiposBoleto", tiposBoleto.count().toString())
            for (tipoBoleto in tiposBoleto) {
                for (document in boletos) {
                    if (document.data?.get("id_Tipo_Boleto") == tipoBoleto.data?.get("id_Tipo_Boleto")) {
                        Log.d("generalProfitsEvent-IF", tipoBoleto.data?.get("precio").toString())
                        ganancias += tipoBoleto.data?.get("precio").toString().toInt()
                    }
                    //Log.d("generalProfitsEvent", document.id.toString())
                }
            }
        }
        return ganancias
    }

    suspend fun getTicketsbyPM(eid:String): Pair<Int,Int> {

        var boletos: QuerySnapshot
        var countTarjeta : Int = 0
        var countEfectivo : Int = 0

        var funciones: QuerySnapshot = db.collection("Funcion")
            .whereEqualTo("id_Evento", eid)
            .get()
            .await()
        Log.d("getTicketsbyPM-Funciones", funciones.count().toString())
        for(element in funciones){
            boletos = db.collection("Boleto")
                .whereEqualTo("id_Funcion", element.id)
                .get()
                .await()
            for(boleto in boletos){
                if(boleto.data?.get("id_Metodo_Pago").toString() == "JsCPG2YuCgqYyZUypktB"){
                    countTarjeta++
                }
                else {
                    countEfectivo++
                }
            }
        }

        val result = Pair(countTarjeta, countEfectivo)
        return result
    }

    suspend fun getTicketTypeSA(eid: String): MutableMap<String, Pair<Int?, Int?>> {

        var boletos: QuerySnapshot
        var diccAsistencias = mutableMapOf<String, Int?>()
        var diccVentas = mutableMapOf<String, Int?>()
        var diccTotales = mutableMapOf<String, Pair<Int?, Int?>>()

        var funciones: QuerySnapshot = db.collection("Evento_Tipo_Boleto")
            .whereEqualTo("id_Evento", eid)
            .get()
            .await()
        Log.d("getTicketTypeSA-Funciones", funciones.count().toString())

        for (element in funciones) {
            boletos = db.collection("Boleto")
                .whereEqualTo("id_Funcion", element.id)
                .get()
                .await()
            Log.d("getTicketTypeSA-Boletos", boletos.count().toString())

            for(boleto in boletos){
                if(boleto.data?.get("id_Tipo_Boleto").toString() !in diccAsistencias){
                    var countVal = 0
                    diccAsistencias.put(boleto.data?.get("id_Tipo_Boleto").toString(), countVal)
                    diccVentas.put(boleto.data?.get("id_Tipo_Boleto").toString(), countVal)
                }

                if(boleto.data?.get("activo").toString() == "false"){
                    diccAsistencias.computeIfPresent(boleto.data?.get("id_Tipo_Boleto").toString()) { _, v -> v + 1}
                    diccVentas.computeIfPresent(boleto.data?.get("id_Tipo_Boleto").toString()) { _, v -> v + 1}
                } else {
                    diccVentas.computeIfPresent(boleto.data?.get("id_Tipo_Boleto").toString()) { _, v -> v + 1}
                }
            }

            var tiposBoleto: QuerySnapshot = db.collection("Tipo_Boleto")
                .get()
                .await()
            Log.d("getTicketTypeSA-tiposBoleto", tiposBoleto.count().toString())

            for ((k, v) in diccVentas) {
                for (tipoBoleto in tiposBoleto) {
                    if(tipoBoleto.id == k) {
                        var countVal: Pair<Int?, Int?> = Pair(v, diccAsistencias.get(k))
                        diccTotales.put(tipoBoleto.data?.get("nombre_Tipo_Boleto").toString(), countVal)
                    }
                }
            }

        }
        Log.d("getTicketTypeSA-diccTotales", diccTotales.toString())
        return diccTotales
    }

}