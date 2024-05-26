package com.raquelbytes.grapeguard.API.Interface

import com.raquelbytes.grapeguard.API.Model.Vinedo

interface VinedoObtenidoCallback {
    fun onVinedoObtenido(vinedo: Vinedo)
    fun onVinedoError(errorMessage: String)
}