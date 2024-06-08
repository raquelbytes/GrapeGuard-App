package com.raquelbytes.grapeguard.Util


object ApiMap {
    const val BASE_URL = "http://192.168.1.140:8080"

    // Usuarios
    const val USUARIO_BASE = "/usuarios"
    const val USUARIO_POR_ID = "/usuario/{ID_usuario}"
    const val USUARIO_LOGIN = "/login"
    const val USUARIO_REGISTER = "/register"
    const val USUARIO_MODIFICAR = "/modify/{ID_usuario}"

    // Viñedos
    const val VINEDO_ID = "/vinedo/{id}"
    const val VINEDO_USUARIO_ID = "/vinedo/usuario/{ID_usuario}"
    const val VINEDO_USUARIO_NUEVO = "/vinedo/usuario/{ID_usuario}/nuevo"


    // Tareas
    const val TAREA_POR_VINEDO_ID = "/tarea/vinedo/{idVinedo}"
    const val TAREA_PENDIENTES_POR_VINEDO_ID = "/tarea/vinedo/{idVinedo}/pendientes"
    const val TAREA_TERMINADAS_POR_VINEDO_ID = "/tarea/vinedo/{idVinedo}/terminadas"
    const val TAREA_AGREGAR_POR_VINEDO_ID = "/tarea/vinedo/{idVinedo}"
    const val TAREA_ELIMINAR_POR_ID = "/tarea/delete/{idTarea}"

    // Cosechas
    const val COSECHA_POR_VINEDO_ID = "/cosecha/vinedo/{idVinedo}"
    const val COSECHA_AGREGAR_POR_VINEDO_ID = "/cosecha/vinedo/{idVinedo}/nuevo"
    const val COSECHA_ELIMINAR_POR_ID = "/cosecha/delete/{id}"
}
