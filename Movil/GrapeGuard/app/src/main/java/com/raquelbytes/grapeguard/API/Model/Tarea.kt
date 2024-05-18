package com.raquelbytes.grapeguard.API.Model

import java.time.LocalDateTime

class Tarea{
    var id: Int? = null
    var vinedo: Vinedo? = null
    var tarea: String? = null
    var estado: EstadoTarea? = null
    var recordatorio: String? = null
    var fechaRealizacion: String? = null

    constructor()

    constructor(id: Int, vinedo: Vinedo, tarea: String, estado: EstadoTarea, recordatorio: String, fechaRealizacion: String) {
        this.id = id
        this.vinedo = vinedo
        this.tarea = tarea
        this.estado = estado
        this.recordatorio = recordatorio
        this.fechaRealizacion = fechaRealizacion
    }
}
enum class EstadoTarea {
    Pendiente,
    EnProgreso,
    Completada
}
