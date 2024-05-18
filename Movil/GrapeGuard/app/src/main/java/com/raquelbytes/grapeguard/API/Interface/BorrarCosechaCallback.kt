package com.raquelbytes.grapeguard.API.Interface

interface BorrarCosechaCallback {
    fun onCosechaBorrada(message: String)
    fun onError(errorMessage: String)
}