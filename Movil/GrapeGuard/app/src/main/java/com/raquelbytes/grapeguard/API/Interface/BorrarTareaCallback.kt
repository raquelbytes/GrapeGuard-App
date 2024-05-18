package com.raquelbytes.grapeguard.API.Interface

interface BorrarTareaCallback {
    // Método llamado cuando se borra la tarea con éxito
    fun onTareaBorrada(message: String)

    // Método llamado en caso de error al borrar la tarea
    fun onError(errorMessage: String)
}