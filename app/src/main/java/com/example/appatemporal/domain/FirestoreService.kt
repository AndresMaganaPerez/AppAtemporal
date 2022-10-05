package com.example.appatemporal.domain

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.appatemporal.data.constants.Constantes.Companion.idCategoria
import com.example.appatemporal.domain.models.*
import com.example.appatemporal.framework.view.AcivityAddCategoria
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Integer.parseInt
import java.util.*
import kotlin.collections.HashMap

class FirestoreService {
    private val db = Firebase.firestore

    suspend fun addUser(uid: String, user: UserModel) {
        db.collection("Usuario")
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                Log.d("FirestoreLogs", "Added User Correctly")
            }
            .await()
    }

    suspend fun addUserRole(uid: String, role: String) {
        val dbRole = db.collection("Rol")
            .whereEqualTo("nombre_Rol", role)
            .get()
            .addOnSuccessListener {
                Log.d("FirestoreLogs", "Got Role Correctly: ${it.documents[0].id}")
            }.await()

        val userRole = hashMapOf(
            "id_Usuario" to uid,
            "id_Rol" to dbRole.documents[0].id
        )

        db.collection("Usuario_Rol")
            .add(userRole)
            .addOnSuccessListener {
                Log.d("FirestoreLogs", "Added User wih Role Correctly")
            }
            .addOnFailureListener {
                Log.d("FirestoreLogs", "Added user failed, exception: $it")
            }
    }

    suspend fun verifyUser(uid: String): Boolean {
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

    suspend fun getUser(uid: String): DocumentSnapshot {
        var userData: DocumentSnapshot =
            db.collection("Usuario")
                .document(uid)
                .get()
                .await()
        return userData
    }

    suspend fun getUserRole(uid: String): DocumentSnapshot {
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

    suspend fun getUserTickets(uid: String): MutableList<GetTicketModel> {
        var result: MutableList<GetTicketModel> = arrayListOf()
        var ticket: GetTicketModel = GetTicketModel()
        var boletos: QuerySnapshot =
            db.collection("Boleto")
                .whereEqualTo("id_Usuario", uid)
                .get()
                .await()
        for (boleto in boletos) {
            var funciones: QuerySnapshot =
                db.collection("Funcion")
                    .whereEqualTo(FieldPath.documentId(), boleto.data?.get("id_Funcion"))
                    .get()
                    .await()
            var evento: QuerySnapshot =
                db.collection("Evento")
                    .whereEqualTo(
                        FieldPath.documentId(),
                        funciones.documents[0].data?.get("id_Evento")
                    )
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

    suspend fun eventCount(uid: String): Int {
        var events: QuerySnapshot =
            db.collection("Usuario_Evento")
                .whereEqualTo("id_Usuario", uid)
                .get()
                .await()
        return events.count()
    }

    suspend fun ventasCount(uid: String): Pair<Int, Int> {
        var ventasCount: Int = 0
        var asistenciasCount: Int = 0
        var ventas: QuerySnapshot =
            db.collection("Usuario_Evento")
                .whereEqualTo("id_Usuario", uid)
                .get()
                .await()
        for (document in ventas) {
            var funciones: QuerySnapshot =
                db.collection("Funcion")
                    .whereEqualTo("id_Evento", document.data?.get("id_Evento"))
                    .get()
                    .await()
            for (document in funciones) {
                var boletosAuxVentas: QuerySnapshot =
                    db.collection("Boleto")
                        .whereEqualTo("id_Funcion", document.id)
                        .get()
                        .await()
                ventasCount += boletosAuxVentas.count()
            }
            for (document in funciones) {
                var boletosAuxAsistencias: QuerySnapshot =
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

    suspend fun getRating(uid: String): Float {
        var acumulado = 0f
        var count = 0f
        var events: QuerySnapshot =
            db.collection("Usuario_Evento")
                .whereEqualTo("id_Usuario", uid)
                .get()
                .await()
        for (document in events) {
            var feedbacks: QuerySnapshot =
                db.collection("Feedback")
                    .whereEqualTo("id_Evento", document.data?.get("id_Evento"))
                    .get()
                    .await()
            for (document in feedbacks) {
                acumulado += document.data?.get("rating").toString().toInt()
                count += 1
            }
        }
        if (count <= 0) return 0f
        return (acumulado / count).toFloat()
    }

    suspend fun getRevenue(uid: String): Int {
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

    suspend fun getTicketDropDown(idEvent: String): List<Triple<String, Int, String>> {
        var dropDown: MutableList<Triple<String, Int, String>> = mutableListOf()
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

    suspend fun currentTicketsFun(
        idEvent: String,
        idFuncion: String
    ): List<Triple<String, Int, Int>> {
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
            maxCountEvent.add(
                Triple(
                    document.data?.get("id_Tipo_Boleto").toString(),
                    boletosEventoTipo.documents.size,
                    parseInt(document.data?.get("max_Boletos").toString())
                )
            )
        }
        return maxCountEvent
    }

    suspend fun RegisterSale(idFuncion: String, id_Metodo_Pago: String, id_Tipo_Boleto: String) {
        var currentDate = Date()
        db.collection("Boleto")
            .document()
            .set(
                TicketModel(
                    true,
                    "RegistroEnTaquilla",
                    idFuncion,
                    id_Metodo_Pago,
                    id_Tipo_Boleto,
                    currentDate,
                    currentDate
                )
            )
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

    suspend fun getEventName(eid: String): String {
        var event: DocumentSnapshot =
            db.collection("Evento")
                .document(eid)
                .get()
                .await()
        return event.data?.get("nombre_Evento").toString()
    }

    suspend fun generalProfitsEvent(eid: String): Int {

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

    suspend fun getTicketsbyPM(eid: String): Pair<Int, Int> {

        var boletos: QuerySnapshot
        var countTarjeta: Int = 0
        var countEfectivo: Int = 0

        var funciones: QuerySnapshot = db.collection("Funcion")
            .whereEqualTo("id_Evento", eid)
            .get()
            .await()
        Log.d("getTicketsbyPM-Funciones", funciones.count().toString())
        for (element in funciones) {
            boletos = db.collection("Boleto")
                .whereEqualTo("id_Funcion", element.id)
                .get()
                .await()
            for (boleto in boletos) {
                if (boleto.data?.get("id_Metodo_Pago").toString() == "JsCPG2YuCgqYyZUypktB") {
                    countTarjeta++
                } else {
                    countEfectivo++
                }
            }
        }

        val result = Pair(countTarjeta, countEfectivo)
        return result
    }

    suspend fun addEvent(event: EventModel, funcion: FunctionModel) {
        db.collection("Evento")
            .add(event)
            .addOnSuccessListener {
                Log.d("Firestore Log = ", "Se agregó correctamente el evento " + event.nombre)
            }
            .await()

        // TODO: Obtener id del nuevo evento creado

        db.collection("Funcion")
            .add(funcion)
            .addOnSuccessListener {
                Log.d(
                    "Firestore Log = ",
                    "Se agregó correctamente las funciones del evento " + event.nombre
                )
            }
            .await()

    }

    suspend fun addEvent2(event: EventModel, artista: String, funcion: FunctionModel, userUid: String, boletos: EventoTipoBoletoModel, cid: String) {
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
            "id_tipo_boleto_fk" to idtipoboleto,
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

    //Ejecutar antes de query addEventoTipoBoleto
    suspend fun getTipoBoletoId(nombre_tipo_boleto: String): String {
        var idTipoBoleto = db.collection("Tipo_Boleto")
            .whereEqualTo("nombre", nombre_tipo_boleto)
            .get()
            .await()
        return idTipoBoleto.documents[0].id
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

