package com.raquelbytes.grapeguard.API.Interface

import com.raquelbytes.grapeguard.API.Model.Vinedo
import java.math.BigDecimal

interface VinedoCallback {
    //fun onVinedoObtenido(vinedo: Vinedo)
    fun onVinedoAgregado(response: String)
    fun onVinedoError(errorMessage: String)
}