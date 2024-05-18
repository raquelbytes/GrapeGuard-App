package com.raquelbytes.grapeguard.API.Model

import java.math.BigDecimal
import java.time.LocalDate

 class Cosecha{
    var id: Int? = null
    var vinedo: Vinedo? = null
    var nombreVariedad: String? = null
    var cantidadUvas: BigDecimal? = null
    var precioVentaKg: BigDecimal? = null
    var fechaCosecha: String? = null

constructor()

constructor(id: Int, vinedo: Vinedo, nombreVariedad: String, cantidadUvas: BigDecimal, precioVentaKg: BigDecimal, fechaCosecha: String) {
    this.id = id
    this.vinedo = vinedo
    this.nombreVariedad = nombreVariedad
    this.cantidadUvas = cantidadUvas
    this.precioVentaKg = precioVentaKg
    this.fechaCosecha = fechaCosecha
}
}
