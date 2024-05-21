package com.raquelbytes.grapeguard.API.Interface

import com.raquelbytes.grapeguard.API.Model.Cosecha

interface AgregarCosechaCallback {
    fun onCosechaAgregada(message: String)
    fun onError(errorMessage: String)
}
