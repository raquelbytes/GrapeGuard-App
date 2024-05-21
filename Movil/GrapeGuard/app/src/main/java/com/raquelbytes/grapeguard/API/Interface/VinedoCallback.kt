package com.raquelbytes.grapeguard.API.Interface

import com.raquelbytes.grapeguard.API.Model.Vinedo
import java.math.BigDecimal

interface VinedoCallback {
    //fun onVinedoObtenido(vinedo: Vinedo)
    fun onVinedoAgregado(response: String)
    //fun onVinedoModificado(response: String)
    //fun onVinedoBorrado(response: String)
    //fun onSumaHectareasObtenida(sumaHectareas: BigDecimal)
    fun onVinedoError(errorMessage: String)
}