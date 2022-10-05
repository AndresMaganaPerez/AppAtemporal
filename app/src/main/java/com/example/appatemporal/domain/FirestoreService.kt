package com.example.appatemporal.domain

import android.util.Log
import com.example.appatemporal.domain.models.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import org.w3c.dom.Comment
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

        var boletos : QuerySnapshot =
            db.collection("Boleto")
                .whereEqualTo("id_usuario_fk",uid)
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
            var ticket = GetTicketModel(evento.documents[0].id, evento.documents[0].data?.get("nombre_Evento").toString(),
                funciones.documents[0].data?.get("fecha").toString(), funciones.documents[0].data?.get("hora_Inicio").toString(),
                evento.documents[0].data?.get("lugar").toString(), evento.documents[0].data?.get("direccion").toString(),
                evento.documents[0].data?.get("ciudad").toString(), evento.documents[0].data?.get("estado").toString(),
                boleto.data?.get("hash_qr").toString())

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
        if (events.isEmpty){return 0f}
        for (document in events){
            var feedbacks : QuerySnapshot =
                db.collection("Rating")
                .whereEqualTo("id_Evento",document.data?.get("id_Evento"))
                .get()
                .await()
            if (feedbacks.isEmpty){return 0f}
            for (document in feedbacks){
                acumulado += document.data?.get("rating").toString().toInt()
                count += 1
            }
        }
        if (count <= 0){return 0f}
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
        if (events.isEmpty){return 0}
        for (document in events) {
            var funciones: QuerySnapshot =
                db.collection("Funcion")
                    .whereEqualTo("id_Evento", document.data?.get("id_Evento"))
                    .get()
                    .await()
            if (funciones.isEmpty){return 0}
            for (document in funciones) {
                boletos =
                    db.collection("Boleto")
                        .whereEqualTo("id_Funcion", document.id)
                        .get()
                        .await()
                if (boletos.isEmpty){return 0}
                tiposBoleto =
                    db.collection("Evento_Tipo_Boleto")
                        .whereEqualTo("id_Evento", document.data?.get("id_Evento"))
                        .get()
                        .await()
                if (tiposBoleto.isEmpty){return 0}
                for (tipoBoleto in tiposBoleto) {
                    for (document in boletos) {
                        if (document.data?.get("id_Tipo_Boleto") == tipoBoleto.data?.get("id_Tipo_Boleto")) {
                            ventaTotal += tipoBoleto.data?.get("precio").toString().toInt()
                        }
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
                    if (document.data?.get("id_Tipo_Boleto") == tipoBoleto.data?.get("id_Tipo_Boleto") &&
                            tipoBoleto.data?.get("id_Evento") == element.data?.get("id_Evento")) {
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

    /**
     * Get a state in Boleto collection of Firestore
     * @param hashQr: String
     * @param id_Event: String
     * @return verifyS: Boolean
     */
    suspend fun getState(hashQr:String):Boolean{
        var verifyS = false

        var stateT:QuerySnapshot = db.collection("Boleto")
            .whereEqualTo("hash_qr", hashQr)
            .get()
            .await()
        verifyS = stateT.documents[0].data?.get("activo") as Boolean
        Log.d("conditionTicket", verifyS.toString())
        return  verifyS
    }

    /**
     * Adds a document in Rating collection of Firestore
     * @param idUser: String
     * @param idEvent: String
     * @param rate: Float
     */

   suspend fun addRating(idUser: String, idEvent : String, rate : Float) {
       val rating = RatingModel(idUser, idEvent, rate, Date())
       db.collection("Rating")
           .add(rating)
           .await()
   }
    /**
     * Get a document in Rating collection of Firestore
     * @param idUser: String
     * @param idEvent: String
     * @return existence: Boolean
     */
    suspend fun verifyRatingExistence(idUser: String, idEvent: String) : Boolean {
        var existence: Boolean = false
        val query = db.collection("Rating")
            .whereEqualTo("id_evento_fk", idEvent)
            .whereEqualTo("id_usuario_fk", idUser)
            .get()
            .await()
        if (!query.isEmpty) {
            existence = true
        }
        Log.d("Existence of rating", existence.toString())
        return existence
    }

    suspend fun getTicketTypeSA(eid: String): MutableMap<String, Pair<Int?, Int?>> {
        Log.d("getTicketTypeSA", "ENTRANDO A FUNCION")
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

    suspend fun getRatingByEvent(eid: String) : MutableList<Float> {
        // [0]acumulado,[1]counTotal,[2]count0,[3]count1,[4]count2,
        // [5]count3,[6]count4, [7]count5, [8]ratingProm
        var listRatings = mutableListOf<Float>(0f,0f,0f,0f,0f,0f,0f,0f,0f)
        var emptyRatings = mutableListOf<Float>(0f,0f,0f,0f,0f,0f,0f,0f,0f)
        var ratings = db.collection("Rating")
            .whereEqualTo("id_evento", eid)
            .get()
            .await()
        if (ratings.isEmpty){return emptyRatings}
        for(element in ratings){
            listRatings[0] = listRatings[0] + element.data?.get("rating").toString().toInt()
            listRatings[1] = listRatings[1] + 1
            when(element.data?.get("rating").toString().toInt()) {
                0 -> listRatings[2] = listRatings[2] + 1
                1 -> listRatings[3] = listRatings[3] + 1
                2 -> listRatings[4] = listRatings[4] + 1
                3 -> listRatings[5] = listRatings[5] + 1
                4 -> listRatings[6] = listRatings[6] + 1
                else -> {
                    listRatings[7] = listRatings[7] + 1
                }
            }
        }
        listRatings[8] = listRatings[0]/listRatings[1]
        if (listRatings[1] <= 0){return emptyRatings}
        return listRatings
    }


    suspend fun addComment(idUser: String,idEvent: String,comment: String){
        var comment = CommentModel(idUser,idEvent,comment,Date())
        db.collection("Comentario")
            .add(comment)
            .await()
    }

    suspend fun getComments(idEvent: String) : QuerySnapshot {
        val comments =
            db.collection("Comentario")
                .whereEqualTo("id_evento_fk", idEvent)
                .get()
                .await()
        return comments
    }

    suspend fun getEventTicketsSA(eid : String) : Pair<Int, Int> {
        var ventasCount : Int = 0
        var asistenciasCount : Int = 0
        val errorHandler: Pair<Int,Int> = Pair(0,0)
        var funciones : QuerySnapshot =
            db.collection("Funcion")
                .whereEqualTo("id_Evento",eid)
                .get()
                .await()
        if (funciones.isEmpty){return errorHandler}
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
            //if (boletosAuxAsistencias.isEmpty){return errorHandler}
            asistenciasCount += boletosAuxAsistencias.count()
        }
        val result = Pair(ventasCount, asistenciasCount)
        return result
    }

    suspend fun getOrganizerEvents(uid : String) : MutableList<EventModel01> {
        var result : MutableList<EventModel01> = arrayListOf()

        // Se buscan los eventos relacionados con el usuario que llega como parámetro.
        var usuarioEventos : QuerySnapshot =
            db.collection("Usuario_Evento")
                .whereEqualTo("id_usuario",uid)
                .get()
                .await()

        // De todos esos registros, se busca cada evento en la tabla de eventos
        for (usuarioEvento in usuarioEventos){
            var eventos : QuerySnapshot =
                db.collection("Evento")
                    .whereEqualTo(FieldPath.documentId(),usuarioEvento.data?.get("id_evento"))
                    .get()
                    .await()
            for (evento in eventos){
                var newEvent = EventModel01(
                    evento.id,
                    evento.data?.get("nombre").toString(),
                    evento.data?.get("descripcion").toString(),
                    evento.data?.get("ciudad").toString(),
                    evento.data?.get("estado").toString(),
                    evento.data?.get("ubicacion").toString(),
                    evento.data?.get("direccion").toString(),
                    evento.data?.get("longitud").toString(),
                    evento.data?.get("latitud").toString(),
                    evento.data?.get("foto_portada").toString(),
                    evento.data?.get("video").toString(),
                    evento.data?.get("activo").toString(),
                    evento.data?.get("aprobado").toString()
                )
                result.add(newEvent)
            }

            Log.d("LOG Evento",result.toString())
        }
        Log.d("LOG Empty Event",result.isEmpty().toString())
        return result
    }

    suspend fun getFunctionOrganizador(eid: String) : MutableList<FuncionModel>{
        var result : MutableList<FuncionModel> = arrayListOf()

        var funciones : QuerySnapshot =
            db.collection("Funcion")
                .whereEqualTo("id_evento_fk", eid)
                .get()
                .await()
        for(funcion in funciones){
            var newFuncion = FuncionModel(
                funcion.data?.get("fecha_funcion").toString(),
                funcion.data?.get("hora_inicio").toString(),
                funcion.data?.get("hora_fin").toString()
            )
            result.add(newFuncion)
        }
        return result
    }
}
