package com.raquelbytes.grapeguard.API.Interface

import com.raquelbytes.grapeguard.API.Model.Vinedo

interface VinedosCallback {
    fun onVinedosObtenidos(vinedos: List<Vinedo>)
    fun onVinedoError(errorMessage: String)
}
