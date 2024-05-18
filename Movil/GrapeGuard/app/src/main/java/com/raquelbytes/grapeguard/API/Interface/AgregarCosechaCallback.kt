package com.raquelbytes.grapeguard.API.Interface

import com.raquelbytes.grapeguard.API.Model.Cosecha

interface AgregarCosechaCallback {
    fun onCosechaAgregada(cosecha: Cosecha)
    fun onError(errorMessage: String)
}
