package com.raquelbytes.grapeguard.API.Model

import java.io.Serializable
import java.math.BigDecimal

class Vinedo : Serializable {
    var id: Int? = null
    var usuarioId: Int? = null
    var nombre: String? = null
    var ubicacion: String? = null
    var fechaPlantacion: String? = null
    var hectareas: BigDecimal? = null

    constructor()

    constructor(id: Int, usuarioId: Int, nombre: String, ubicacion: String, fechaPlantacion: String, hectareas: BigDecimal) {
        this.id = id
        this.usuarioId = usuarioId
        this.nombre = nombre
        this.ubicacion = ubicacion
        this.fechaPlantacion = fechaPlantacion
        this.hectareas = hectareas
    }
}
