package com.example.appatemporal.domain

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.appatemporal.data.constants.Constantes.Companion.idCategoria
import com.example.appatemporal.domain.models.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Boolean.parseBoolean
import java.lang.Double.parseDouble
import org.w3c.dom.Comment
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
            var evento : DocumentSnapshot =
                db.collection("Evento")
                    .document(funciones.documents[0].data?.get("id_Evento").toString())
                    .get()
                    .await()
            Log.d("EventLog", evento.data.toString())
            var ticket = GetTicketModel(evento.id, evento.data?.get("nombre_Evento").toString(),
                funciones.documents[0].data?.get("fecha").toString(), funciones.documents[0].data?.get("hora_Inicio").toString(),
                evento.data?.get("lugar").toString(), evento.data?.get("direccion").toString(),
                evento.data?.get("ciudad").toString(), evento.data?.get("estado").toString(),
                boleto.data?.get("hash_qr").toString())

            result.add(ticket)

            //Log.d("LOG ticket information from API",ticket.toString())

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

    suspend fun verifyTicketExistence(resulted: String) : Boolean {
        var existence: Boolean = false
        var query = db.collection("Boleto")
            .whereEqualTo("hash_QR", resulted)
            .get()
            .await()
        if (!query.isEmpty) {
            existence = true
        }
        return existence
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

    suspend fun getMetodoPagoId(metodoPago: String) : QuerySnapshot {
        val query = db.collection("Metodo_Pago")
            .whereEqualTo("metodo", metodoPago)
            .get()
            .await()
        return query
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

    suspend fun getTicketsbyPM(eid:String): MutableMap<String, Int?> {

        var diccPM = mutableMapOf<String, Int?>()

        var funciones: QuerySnapshot = db.collection("Funcion")
            .whereEqualTo("id_evento_fk", eid)
            .get()
            .await()
        Log.d("getTicketsbyPM-Funciones", funciones.count().toString())
        if (funciones.isEmpty){diccPM.put("No hay datos", 0); return diccPM}

        for(element in funciones){
            var boletos : QuerySnapshot = db.collection("Boleto")
                .whereEqualTo("id_funcion_fk", element.id)
                .get()
                .await()
            if (boletos.isEmpty){diccPM.put("No hay datos", 0); return diccPM}

            for(boleto in boletos){
                if(boleto.data?.get("id_metodo_pago_fk").toString() !in diccPM){
                    diccPM.put(boleto.data?.get("id_metodo_pago_fk").toString(), 0)
                }
                diccPM.computeIfPresent(boleto.data?.get("id_metodo_pago_fk").toString()) { _, v -> v + 1}
            }
        }
        var metodos : QuerySnapshot =
            db.collection("Metodo_Pago")
                .get()
                .await()
        if (metodos.isEmpty){diccPM.put("No hay datos en Metodos", 0); return diccPM}

        var result = mutableMapOf<String, Int?>()
        for (element in diccPM){
            for (metodo in metodos){
                if (element.key == metodo.id){
                    result.put(metodo.data?.get("nombre").toString(),diccPM.get(element.key))
                }
            }
        }
        Log.d("Dentro de getTicketsbyPM",result.toString())
        if (result.isEmpty()){result.put("No hay datos", 0); return result}
        return result
    }

    suspend fun getEvents(): List<EventModel>{
        var events: MutableList<EventModel> = mutableListOf()
        var event: QuerySnapshot = db.collection("Evento")
            .get()
            .await()
        for (document in event) {
            events.add(
                EventModel(
                    document.id,
                    document.data?.get("nombre").toString(),
                    document.data?.get("descripcion").toString(),
                    document.data?.get("ciudad").toString(),
                    document.data?.get("estado").toString(),
                    document.data?.get("ubicacion").toString(),
                    document.data?.get("direccion").toString(),
                    document.data?.get("longitud").toString(),
                    document.data?.get("latitud").toString(),
                    document.data?.get("foto_portada").toString(),
                    document.data?.get("video").toString(),
                    document.data?.get("activo").toString(),
                    document.data?.get("aprobado").toString(),

                    )
            )
        }
        return events
    }

    /*suspend fun getCategories(): List<CategoryModel>{
        Log.d("Test1", "firestore")
        var categories: MutableList<CategoryModel> = mutableListOf()
        var category: QuerySnapshot = db.collection("Categoria")
            .get()
            .await()
        for (document in category) {
            categories.add(
                CategoryModel(
                    document.id,
                    document.data?.get("nombre").toString(),
                    parseBoolean(document.data?.get("visibilidad").toString())
                )
            )
        }
        return categories
    }*/

    suspend fun getIdsOfEventosWithidCategoria(idCategoria: String): List<String>{
        var ids: MutableList<String> = mutableListOf()
        var eventos: QuerySnapshot = db.collection("Evento_Categoria")
            .whereEqualTo("id_categoria_fk", idCategoria)
            .get()
            .await()
        for (document in eventos) {
            ids.add(document.data?.get("id_evento_fk").toString())
        }
        return ids
    }

    suspend fun getCategoryIdByName(name: String): String{
        var category: QuerySnapshot = db.collection("Categoria")
            .whereEqualTo("nombre", name)
            .get()
            .await()
        return category.documents[0].id
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
        //Log.d("Existence of rating", existence.toString())
        return existence
    }

    suspend fun verifyCommentExistence(idUser: String, idEvent: String) : Boolean {
        var existence: Boolean = false
        val query = db.collection("Comentario")
            .whereEqualTo("id_evento_fk", idEvent)
            .whereEqualTo("id_usuario_fk", idUser)
            .get()
            .await()
        if (!query.isEmpty) {
            existence = true
        }
        //Log.d("Existence of comment", existence.toString())
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
        //[0]acumulado,[1]counTotal,[2]count0,[3]count1,[4]count2,
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

    suspend fun getRevenuebyPM(eid:String): MutableMap<String, Int?> {
        var diccPM = mutableMapOf<String, Int?>()
        var errorHandler : MutableMap<String, Int?> = mutableMapOf(Pair("Sin ventas por el momento",0))

        var funciones: QuerySnapshot = db.collection("Funcion")
            .whereEqualTo("id_Evento", eid)
            .get()
            .await()
        Log.d("getTicketsbyPM-Funciones", funciones.count().toString())
        if (funciones.isEmpty){diccPM.put("No hay datos en Funcionces", 0); return diccPM}

        var tiposboleto: QuerySnapshot = db.collection("Evento_Tipo_Boleto")
            .whereEqualTo("id_Evento", eid)
            .get()
            .await()
        if (tiposboleto.isEmpty){diccPM.put("No hay datos en Tipos de Boletos", 0); return diccPM}

        for(element in funciones){
            var boletos : QuerySnapshot = db.collection("Boleto")
                .whereEqualTo("id_Funcion", element.id)
                .get()
                .await()
            if (boletos.isEmpty){diccPM.put("No hay datos en Boletos", 0); return diccPM}

            for (boleto in boletos){
                for (tipoBoleto in tiposboleto){
                    if (boleto.data?.get("id_Tipo_Boleto").toString() ==
                        tipoBoleto.data?.get("id_Tipo_Boleto").toString()){
                        if (boleto.data?.get("id_Metodo_Pago").toString() !in diccPM){
                            diccPM.put(boleto.data?.get("id_Metodo_Pago").toString(), 0)
                        }
                        diccPM.computeIfPresent(boleto.data?.get("id_Metodo_Pago").toString())
                        { _, v -> v + tipoBoleto.data?.get("precio").toString().toInt()}
                    }
                }
            }
        }

        var metodos : QuerySnapshot =
            db.collection("Metodo_Pago")
                .get()
                .await()
        if (metodos.isEmpty){diccPM.put("No hay datos en Metodos", 0); return diccPM}

        var result = mutableMapOf<String, Int?>()
        for (element in diccPM){
            for (metodo in metodos){
                if (element.key == metodo.id){
                    result.put(metodo.data?.get("metodo").toString(),diccPM.get(element.key))
                }
            }
        }

        Log.d("Dentro de getRevenuebyPM",result.toString())
        if (result.isEmpty()){return errorHandler}
        return result
    }

    // Querys Andrés y Aldo
    suspend fun getEventsActualMonth(day:Int,month:Int,year:Int) : MutableList<EventsInMonth> {
        var result : MutableList<EventsInMonth> = arrayListOf()
        var events = db.collection("Evento")
            .whereEqualTo("activo",1)
            .whereEqualTo("aprobado",1)
            .get()
            .await()
        for(event in events){
            var functions = db.collection("Funcion")
                .whereEqualTo("id_evento_fk",event.id)
                .get()
                .await()
            for(function in functions){
                var eventDate = function.data?.get("fecha_funcion").toString()
                val arrayDate: List<String> = eventDate.split("/")
                Log.d("ArrayDateLog", arrayDate.toString())
                if(arrayDate[1].toInt()==month && arrayDate[0].toInt() >= day && arrayDate[2].toInt() == year){
                    var evento = EventsInMonth(
                        event.id,
                        event.data?.get("nombre").toString(),
                        event.data?.get("ubicacion").toString(),
                        function.data?.get("hora_inicio").toString(),
                        event.data?.get("foto_portada").toString(),
                        //extra properties
                        event.data?.get("descripcion").toString(),
                        event.data?.get("ciudad").toString(),
                        event.data?.get("estado").toString(),
                        event.data?.get("direccion").toString(),
                        event.data?.get("longitud").toString(),
                        event.data?.get("latitud").toString(),
                        event.data?.get("video").toString(),
                        event.data?.get("activo").toString(),
                        event.data?.get("aprobado").toString(),
                        function.data?.get("fecha_funcion").toString(),
                    )
                    result.add(evento)
                }
            }
        }
        Log.d("LogResult", result.toString())
        return result
    }

    suspend fun getEvents1(): MutableList<EventModel>{
        var events: MutableList<EventModel> = mutableListOf()
        var event: QuerySnapshot = db.collection("Evento")
            .whereEqualTo("activo",1)
            .whereEqualTo("aprobado",1)
            .get()
            .await()
        for (document in event) {
            events.add(
                EventModel(
                    document.id,
                    document.data?.get("nombre").toString(),
                    document.data?.get("descripcion").toString(),
                    document.data?.get("ciudad").toString(),
                    document.data?.get("estado").toString(),
                    document.data?.get("ubicacion").toString(),
                    document.data?.get("direccion").toString(),
                    document.data?.get("longitud").toString(),
                    document.data?.get("latitud").toString(),
                    document.data?.get("foto_portada").toString(),
                    document.data?.get("video").toString(),
                    document.data?.get("activo").toString(),
                    document.data?.get("aprobado").toString()
                )
            )
        }
        return events
    }

    suspend fun getEventsUserOrg(uid:String): MutableList<EventModel>{
        var result: MutableList<EventModel> = mutableListOf()

        var event_user = db.collection("Usuario_Evento")
            .whereEqualTo("id_usuario_fk",uid)
            .get()
            .await()
        Log.d("getEventsUserOrg-uid",uid)
        Log.d("getEventsUserOrg-eventUser",event_user.isEmpty().toString())

        for(element in event_user){
            var event = db.collection("Evento")
                //.document(element.data?.get("id_evento_fk").toString())
                .whereEqualTo(FieldPath.documentId(),element.data?.get("id_evento_fk").toString())
                .get()
                .await()
            Log.d("getEventsUserOrg-Event", event.toString())
            var evento = EventModel(
                event.documents[0].id,
                event.documents[0].data?.get("nombre").toString(),
                event.documents[0].data?.get("descripcion").toString(),
                event.documents[0].data?.get("ciudad").toString(),
                event.documents[0].data?.get("estado").toString(),
                event.documents[0].data?.get("ubicacion").toString(),
                event.documents[0].data?.get("direccion").toString(),
                event.documents[0].data?.get("longitud").toString(),
                event.documents[0].data?.get("latitud").toString(),
                event.documents[0].data?.get("foto_portada").toString(),
                event.documents[0].data?.get("video").toString(),
                event.documents[0].data?.get("activo").toString(),
                event.documents[0].data?.get("aprobado").toString()
            )
            result.add(evento)
        }
        Log.d("getEventsUserOrg", result.toString())
        return result
    }

    // Querys Crear Proyecto
    suspend fun addEvent2(event: CrearEventModel, artista: String, funcion: FunctionModel, userUid: String, boletos: EventoTipoBoletoModel, cid: String) {
        db.collection("Evento")
            .add(event)
            .addOnSuccessListener {
                Log.d("Firestore Log = ", "Se agregó correctamente el evento " + it.id)
                GlobalScope.launch {
                    addArtista(it.id, artista)
                    addFunction(it.id, funcion.fecha_fun, funcion.hora_inicio, funcion.hora_fin)
                    addUsuarioEvento(it.id, userUid)
                    addEventoTipoBoleto(it.id,boletos.id_Tipo_Boleto,boletos.precio,boletos.max_boleto)
                    addEventoCategoria(it.id, cid)
                }
            }
    }
    suspend fun addEventoCategoria(eid: String, cn: String) {
        val cid=getCategory(cn)
        var data = hashMapOf(
            "id_evento_fk" to eid,
            "id_categoria_fk" to cid.documents[0].id
        )
        db.collection("Evento_Categoria")
            .add(data)
            .addOnSuccessListener {
                Log.d(
                    "Firestore Log = ",
                    "Se agregó correctamente el evento por categoria:  " + idCategoria
                )
            }
            .await()
    }

    suspend fun addArtista(eid: String, nombre_artista: String) {
        var data = hashMapOf(
            "id_evento_fk" to eid,
            "nombre_artista" to nombre_artista
        )

        db.collection("Evento_Artista")
            .add(data)
            .addOnSuccessListener {
                Log.d("Firestore Log = ", "Se agregó correctamente el artista:  " + nombre_artista)
            }
            .await()
    }



    suspend fun getEventCategory(): List<String> {
        var dropdown :MutableList<String> = mutableListOf()
        val categorias = db.collection("Categoria").get().await()
        for(categoria in categorias){
            var nombre = categoria.getField<String>("nombre").toString()
            dropdown.add(nombre)
        }
        Log.d("categoria",dropdown[0])
        return dropdown
    }

    suspend fun getCategory(nombre_categoria: String): QuerySnapshot {
        return db.collection("Categoria")
            .whereEqualTo("nombre", nombre_categoria)
            .get()
            .await()
    }

    suspend fun getallCategories(): MutableMap<String, String> {
        var Hashmap_category: MutableMap<String, String> = HashMap<String, String> ()

        var categorias= db.collection("Categoria")
            .get()
            .await()
        for(categoria in categorias){
            Hashmap_category.put(categoria.id, categoria.getField<String>("nombre").toString())
        }
        return Hashmap_category
    }

    suspend fun getEventCategoryFilteredList(eid: String): List<String> {
        var list_categoriaevento :MutableList<String> = mutableListOf()
        val categorias = db.collection("Evento_Categoria")
            .whereEqualTo("id_evento_fk", eid)
            .get()
            .await()

        for(categoria in categorias){
            list_categoriaevento.add(categoria.getField<String>("id_categoria_fk").toString())
        }
        return list_categoriaevento
    }

    suspend fun getEventCategoryFilter(eid: String): List<String> {
        var QS_categoria = getallCategories()
        var list_categoriaevento = getEventCategoryFilteredList(eid)
        var dropdown :MutableList<String> = mutableListOf()

        for ((k, v) in QS_categoria){
            if(k !in list_categoriaevento){
                dropdown.add(v)
            }
        }

        return dropdown
    }

    suspend fun getallTipoBoleto(): MutableMap<String, String> {
        var Hashmap_tb: MutableMap<String, String> = HashMap<String, String>()
        var tb = db.collection("Tipo_Boleto")
            .get()
            .await()
        for(tbs in tb){
            Hashmap_tb.put(tbs.id, tbs.getField<String>("nombre").toString())
        }
        return Hashmap_tb
    }
    suspend fun getEventTBFilteredList(eid: String): List<String> {
        var list_tb_evento :MutableList<String> = mutableListOf()
        val tb = db.collection("Evento_Tipo_Boleto")
            .whereEqualTo("id_evento_fk", eid)
            .get()
            .await()

        for(tbs in tb){
            list_tb_evento.add(tbs.getField<String>("id_tipo_boleto_fk").toString())
        }
        return list_tb_evento
    }

    suspend fun getEventoTipoBoletoFiltered(eid: String): List<String> {
        var QS_tipoevento = getallTipoBoleto()
        var list_tipoevento = getEventTBFilteredList(eid)
        var dropdown :MutableList<String> = mutableListOf()
        for ((k, v) in QS_tipoevento){
            if(k !in list_tipoevento){
                dropdown.add(v)
            }
        }
        return dropdown
    }

    suspend fun addUsuarioEvento(eid: String, uid: String) {
        var data = hashMapOf(
            "id_usuario_fk" to uid,
            "id_evento_fk" to eid
        )
        db.collection("Usuario_Evento")
            .add(data)
            .addOnSuccessListener {
                Log.d(
                    "Firestore Log = ",
                    "Se agregó correctamente usuario por evento:  " + uid
                )
            }
            .await()
    }
    suspend fun GetTipoBoleto(nombre_tb: String): QuerySnapshot {
        return db.collection("Tipo_Boleto")
            .whereEqualTo("nombre", nombre_tb)
            .get()
            .await()
    }

    suspend fun addEventoTipoBoleto(
        eid: String,
        tipoboleto: String,
        precio: Int,
        max_boletos: Int
    ) {
        var idtipoboleto=GetTipoBoleto(tipoboleto)
        var data = hashMapOf(
            "id_evento_fk" to eid,
            "id_tipo_boleto_fk" to idtipoboleto.documents[0].id,
            "precio" to precio,
            "max_boletos" to max_boletos
        )
        db.collection("Evento_Tipo_Boleto")
            .add(data)
            .addOnSuccessListener {
                Log.d(
                    "Firestore Log = ",
                    "Se agregó correctamente el tipo de boleto:   " + eid
                )
            }
            .await()
    }


    suspend fun addFunction(
        eid: String,
        fechaFuncion: String,
        HoraInicio: String,
        HoraFin: String
    ) {
        var data = hashMapOf(
            "id_evento_fk" to eid,
            "fecha_funcion" to fechaFuncion,
            "hora_incio" to HoraInicio,
            "hora_fin" to HoraFin
        )
        db.collection("Funcion")
            .add(data)
            .addOnSuccessListener {
                Log.d(
                    "Firestore Log = ",
                    "Se agregó correctamente la funcion:   " + fechaFuncion
                )
            }
            .await()
    }
}

