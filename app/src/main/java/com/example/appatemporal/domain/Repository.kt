package com.example.appatemporal.domain

import android.content.Context
import android.util.Log
import com.example.appatemporal.data.localdatabase.LocalDatabase
import com.example.appatemporal.data.localdatabase.entities.*
import com.example.appatemporal.domain.models.*
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class Repository(context: Context) {

    // Firestore
    private val firestoreAPI = FirestoreService()

    suspend fun addUser(uid: String, user: UserModel, role: String) {
        firestoreAPI.addUser(uid, user)
        firestoreAPI.addUserRole(uid, role)
    }

    suspend fun verifyUser(uid: String) : Boolean {
        return firestoreAPI.verifyUser(uid)
    }

    suspend fun getUser(uid: String) : DocumentSnapshot{
        return firestoreAPI.getUser(uid)
    }

    suspend fun getUserRole(uid: String) : DocumentSnapshot {
        return firestoreAPI.getUserRole(uid)
    }

    suspend fun eventCount(uid: String) : Int {
        return firestoreAPI.eventCount(uid)
    }

    suspend fun ventasCount(uid: String) : Pair<Int, Int> {
        return firestoreAPI.ventasCount(uid)
    }

    suspend fun getRating(uid: String) : Float {
        return firestoreAPI.getRating(uid)
    }

    suspend fun getRevenue(uid: String) : Int {
        return firestoreAPI.getRevenue(uid)
    }

    suspend fun updateTicketValue(resulted: String) : Boolean {
        return firestoreAPI.updateTicketValue(resulted)
    }
    /**
     * Asks FireStore Service to get TicketTypes for view Dropdown.
     *
     * @param idEvento Event Id to get TicketTypes from.
     *
     * @return List -> List containing Triples, each containing TicketType Data. Name, Price, and ID.
     */
    suspend fun getTicketDropdown(idEvento: String) : List<Triple<String, Int, String>> {
        return firestoreAPI.getTicketDropDown(idEvento)
    }
    /**
     * Asks FireStore Service to get all tickets registered to a given Function in the given event of a each type.
     *
     * @param idEvento Event Id to get TicketTypes from.
     *
     * @param idFuncion -> Function to take count from.
     *
     * @return List -> List containing Triples, each containing ticket count of each ticketType.
     * EX: ID, TicketCountOfType, Maximum amount of Tickets specific Type can Have.
     */
    suspend fun getCurrentTicketsFun(idEvento: String, idFuncion: String) : List<Triple<String, Int, Int>> {
        return firestoreAPI.currentTicketsFun(idEvento, idFuncion)
    }

    /**
     * Asks Firestore Service to Store a new Registry of A TicketSale.
     * @param idFuncion -> FunctionID to Register Sale to.
     * @param id_Metodo_Pago -> Payment Type Id.
     *@param id_Tipo_Boleto -> Ticket Type to Register.
     */
    suspend fun registerSale(idFuncion: String,id_Metodo_Pago: String,id_Tipo_Boleto: String){
        return firestoreAPI.RegisterSale(idFuncion,id_Metodo_Pago,id_Tipo_Boleto)
    }
    /**
     * Asks FireStore Service to get UID of PaymentType.
     *
     * @param metodoPago Event Id to get TicketTypes from.
     *
     * @return QuerySnapshot -> QuerySnapshot containing the ID of the PaymentType from name.
     */
    suspend fun getMetodoPagoUid(metodoPago: String) : QuerySnapshot {
        return  firestoreAPI.getMetodoPagoId(metodoPago)
    }

    suspend fun getEventName(eid: String) : String{
        return firestoreAPI.getEventName(eid)
    }

    suspend fun generalProfitsEvent(eid: String) : Int{
        return firestoreAPI.generalProfitsEvent(eid)
    }

    suspend fun getTicketsbyPM(eid: String) : MutableMap<String, Int?>{
        return firestoreAPI.getTicketsbyPM(eid)
    }

    suspend fun addFailure(title: String, description: String) {
        firestoreAPI.addFailure(title, description)
    }

    suspend fun getState(hash_Qr:String): Boolean{
        return firestoreAPI.getState(hash_Qr)
    }

    suspend fun addRating(idUser: String, idEvent : String, rate : Float) {
        firestoreAPI.addRating(idUser, idEvent, rate)
    }
    suspend fun verifyRatingExistence(idUser: String, idEvent: String) : Boolean {
        return firestoreAPI.verifyRatingExistence(idUser, idEvent)
    }

    suspend fun getTicketTypeSA(eid: String): MutableMap<String, Pair<Int?, Int?>> {
        return firestoreAPI.getTicketTypeSA(eid)
    }

    suspend fun getRatingByEvent(eid: String): MutableList<Float> {
        return firestoreAPI.getRatingByEvent(eid)
    }

    suspend fun addComment(idUser: String, idEvent: String, comment: String) {
        return firestoreAPI.addComment(idUser,idEvent,comment)
    }

    suspend fun verifyCommentExistence(idUser: String, idEvent: String) : Boolean {
        return firestoreAPI.verifyCommentExistence(idUser, idEvent)
    }

    suspend fun getComments(idEvento: String) : QuerySnapshot {
        return firestoreAPI.getComments(idEvento)
    }

    suspend fun getEventTicketsSA(eid: String) : Pair<Int,Int> {
        return firestoreAPI.getEventTicketsSA(eid)
    }

    suspend fun getEventsActualMonth(eD:Int,eM:Int,eY:Int) : MutableList<EventsInMonth> {
        return firestoreAPI.getEventsActualMonth(eD,eM,eY)
    }

    suspend fun getEvents() : MutableList<EventModel> {
        return firestoreAPI.getEvents()
    }

    suspend fun getEventsUserOrg(uid:String) : MutableList<EventModel> {
        return firestoreAPI.getEventsUserOrg(uid)
    }

    suspend fun verifyTicketExistence(result: String) : Boolean {
        return firestoreAPI.verifyTicketExistence(result)
    }

    suspend fun getRevenuebyPM(eid: String) : MutableMap<String, Int?> {
        return firestoreAPI.getRevenuebyPM(eid)
    }

//    suspend fun getEvents() = firestoreAPI.getEvents()
    suspend fun getCategories() = firestoreAPI.getCategories()
    suspend fun getIdsOfEventosWithidCategoria(idCategoria: String) = firestoreAPI.getIdsOfEventosWithidCategoria(idCategoria)
    suspend fun getCategoryIdByName(name: String) = firestoreAPI.getCategoryIdByName(name)

    // Local database

    val actividadDao = LocalDatabase.getInstance(context).actividadDao
    val areaDao = LocalDatabase.getInstance(context).areaDao
    val estatusDao = LocalDatabase.getInstance(context).estatusDao
    val objetivoDao = LocalDatabase.getInstance(context).objetivoDao
    val costoDao = LocalDatabase.getInstance(context).costoDao
    val proyectoDao = LocalDatabase.getInstance(context).proyectoDao
    val usuarioDao = LocalDatabase.getInstance(context).usuarioDao
    val privilegioDao = LocalDatabase.getInstance(context).privilegioDao
    val rolDao = LocalDatabase.getInstance(context).rolDao


    suspend fun insertActividad(actividad: Actividad) = actividadDao.insert(actividad)
    suspend fun insertAllActividades(actividades: List<Actividad>) =
        actividadDao.insertAll(actividades)

    suspend fun getAllActividades() = actividadDao.getAll()
    suspend fun getActividadById(id: Int) = actividadDao.getById(id)
    suspend fun getAllActividadById(id: Int) = actividadDao.getAllActivityId(id)
    suspend fun deleteActividad(actividad: Actividad) = actividadDao.delete(actividad)
    suspend fun deleteAllActividades() = actividadDao.deleteAll()
    fun countPendingActivities(id_a: Int, stringStatus: String): Int = runBlocking {
        val count = async {
            actividadDao.countPendingActivities(id_a, stringStatus)
        }
        count.start()
        count.await()
    }

    fun countDoneActivities(id_a: Int, stringStatus: String): Int = runBlocking {
        val count = async {
            actividadDao.countDoneActivities(id_a, stringStatus)
        }
        count.start()
        count.await()
    }

    fun countAllActivities(id_a: Int): Int = runBlocking {
        val count = async {
            actividadDao.countAllActivities(id_a)
        }
        count.start()
        count.await()
    }
    //Filter Activities
    suspend fun filterActivitiesByStatus(idProyecto:Int, stringStatus:String) = actividadDao.FilterActivityByStatus(idProyecto, stringStatus)
    suspend fun filterActivitiesByArea(idProyecto:Int, stringStatus:String) = actividadDao.FilterActivityByArea(idProyecto, stringStatus)
    suspend fun filterActivitiesByPriority(idProyecto:Int, stringStatus:String) = actividadDao.FilterActivityByPriority(idProyecto, stringStatus)
    suspend fun updateActividad(nombre:String, estatus:String, area:String, prioridad:String, id: Int) = actividadDao.update(nombre, estatus, area, prioridad, id)

    suspend fun insertArea(area: Area) = areaDao.insert(area)
    suspend fun insertAllAreas(areas: List<Area>) = areaDao.insertAll(areas)
    suspend fun getAllAreas() = areaDao.getAll()
    suspend fun getAreaById(id: Int) = areaDao.getById(id)
    suspend fun deleteArea(area: Area) = areaDao.delete(area)
    suspend fun deleteAllAreas() = areaDao.deleteAll()

    suspend fun insertEstatus(estatus: Estatus) = estatusDao.insert(estatus)
    suspend fun insertAllEstatus(estatus: List<Estatus>) = estatusDao.insertAll(estatus)
    suspend fun getAllEstatus() = estatusDao.getAll()
    suspend fun getEstatusById(id: Int) = estatusDao.getById(id)
    suspend fun deleteEstatus(estatus: Estatus) = estatusDao.delete(estatus)
    suspend fun deleteAllEstatus() = estatusDao.deleteAll()

    suspend fun insertObjetivo(objetivo: Objetivo) = objetivoDao.insert(objetivo)
    suspend fun insertAllObjetivos(objetivos: List<Objetivo>) = objetivoDao.insertAll(objetivos)
    suspend fun getAllObjetivos() = objetivoDao.getAll()
    suspend fun getObjetivoById(id: Int) = objetivoDao.getById(id)
    suspend fun deleteObjetivo(objetivo: Objetivo) = objetivoDao.delete(objetivo)
    suspend fun deleteAllObjetivos() = objetivoDao.deleteAll()


    suspend fun insertProyecto(proyecto: Proyecto) = proyectoDao.insert(proyecto)
    suspend fun insertAllProyectos(proyectos: List<Proyecto>) = proyectoDao.insertAll(proyectos)
    suspend fun getAllProyectos() = proyectoDao.getAll()
    suspend fun getProyectoById(id: Int) = proyectoDao.getById(id)
    suspend fun deleteProyecto(proyecto: Proyecto) = proyectoDao.delete(proyecto)
    suspend fun deleteAllProyectos() = proyectoDao.deleteAll()
    suspend fun updateProyecto(proyecto: Proyecto) = proyectoDao.update(proyecto)
    suspend fun updatePresupuesto(presupuestoN: Double, id: Int) =
        proyectoDao.updatePresupuesto(presupuestoN, id)

    suspend fun updateMeta(metaN: Double, id: Int) = proyectoDao.updateMeta(metaN, id)
    suspend fun updateEstatusCompletado(estatusN: Boolean, id: Int) = proyectoDao.updateEstatusCompletado(estatusN, id)
    suspend fun filterProjectsByStatus(stringStatus:Boolean) = proyectoDao.FilterProjectsByStatus(stringStatus)
    suspend fun updateModifyProyect(name: String, date: String, time: String, id: Int) =
        proyectoDao.updateModify(name, date, time, id)


    suspend fun insertCosto(costo: Costo) = costoDao.insert(costo)
    suspend fun insertAllCostos(costos: List<Costo>) = costoDao.insertAll(costos)
    suspend fun getAllCostos(id: Int) = costoDao.getAll(id)
    suspend fun getCostoById(id: Int) = costoDao.getById(id)
    suspend fun deleteCosto(costo: Costo) = costoDao.delete(costo)
    suspend fun deleteAllCostos() = costoDao.deleteAll()
    suspend fun updateCosto(costo: Costo) = costoDao.update(costo)


    suspend fun getUserTickets(uid: String): MutableList<GetTicketModel> {
        Log.d("LOG Repositorio", firestoreAPI.getUserTickets(uid).toString())
        return firestoreAPI.getUserTickets(uid)
    }


    suspend fun addUserLocalDB(user: Usuario) = usuarioDao.insertUserLocalDB(user)
    suspend fun getUserLocalDB(userUid: String) : Usuario = usuarioDao.getUserLocalDB(userUid)



    //CreateEvent



    suspend fun addEvent2(event: CreateEventModel, artista: String, funcion: FunctionModel, userUid: String, boletos: EventoTipoBoletoModel, cid: String) {
        firestoreAPI.addEvent2(event, artista, funcion, userUid, boletos, cid)
    }
    suspend fun addFunction(eid: String, fechaFuncion: String, HoraInicio:String, HoraFin:String){
        firestoreAPI.addFunction(eid,fechaFuncion,HoraInicio,HoraFin)
    }

    suspend fun addEventoCategoria(eid: String, idCategoria: String){
        firestoreAPI.addEventoCategoria(eid, idCategoria)
    }

    suspend fun AddEventoTipoBoleto(eid: String, tipoboleto: String, precio: Int, cantidad: Int){
        firestoreAPI.addEventoTipoBoleto(eid, tipoboleto, precio, cantidad)
    }

    suspend fun addArtista(eid: String, nombre_artista: String){
        firestoreAPI.addArtista(eid, nombre_artista)
    }

    suspend fun getCategoriaEvento():List<String>{
        return firestoreAPI.getEventCategory()
    }

    suspend fun getCategoriaEventoFilter(eid:String):List<String>{
        return firestoreAPI.getEventCategoryFilter(eid)
    }

    suspend fun getEventoTipoBoletoFiltered(eid:String):List<String>{
        return firestoreAPI.getEventoTipoBoletoFiltered(eid)
    }

    //Obtener eventos organizador

    suspend fun getOrganizerEvent(uid: String): MutableList<EventModel01> {
        return firestoreAPI.getOrganizerEvents(uid)
    }

    suspend fun getFunctionOrganizer(eid: String): MutableList<FuncionModel> {
        return firestoreAPI.getFunctionOrganizador(eid)
    }


}
