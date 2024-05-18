package com.raquelbytes.grapeguard.API.Interface


interface AgregarTareaCallback {
    // Método llamado cuando se agrega la tarea con éxito
    fun onTareaAgregada(message: String)

    // Método llamado en caso de error al agregar la tarea
    fun onError(errorMessage: String)
}
