package com.raquelbytes.grapeguard.API.Interface

import com.raquelbytes.grapeguard.API.Model.Cosecha

interface CosechaCallback {
    fun onCosechasObtenidas(cosechas: List<Cosecha>)

    // Método para manejar errores durante la solicitud de cosechas
    fun onError(errorMessage: String)
}
