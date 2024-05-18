package com.raquelbytes.grapeguard.API.Interface

import com.raquelbytes.grapeguard.API.Model.Tarea

interface TareaCallback {
    // Método llamado cuando se obtienen las tareas con éxito
    fun onTareasObtenidas(tareas: List<Tarea>)

    // Método llamado en caso de error al obtener las tareas
    fun onError(errorMessage: String)
}

